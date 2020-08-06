/*
 * Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.muses.cache.mycache;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.muses.cache.mycache.constant.RedisCacheConstant;
import com.muses.cache.mycache.utils.NamedThreadFactory;

/**
 * <pre>
 * 缓存
 * 关于缓存三大问题说明：
 * 缓存穿透：DB和cache均没有数据. 攻击者利用不存在的key攻击DB
 * 缓存击穿：DB有数据, cache没有数据. 热点key, 当某一时刻cache失效, 高并发访问量转发到DB, 导致DB负载
 * 缓存雪崩：cache同一时刻大批量失效
 * 该缓存针对以上问题均提供了一些解决方案
 * </pre>
 *
 * @param <K> 参数
 * @param <V> 返回结果
 * @author Jervis
 * @date 2018/10/17.
 */
public abstract class AbstractCache<K extends CacheParam, V> implements ICache<K, V> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 缓存操作线程池
     */
    protected static final ThreadPoolExecutor setDataToRedisCachePool;

    @Resource
    protected RedissonClient redissonClient;

    static {
        setDataToRedisCachePool = new ThreadPoolExecutor(10, 10, 10L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),
            new NamedThreadFactory("SetDataToRedisCachePool"));
        setDataToRedisCachePool.allowCoreThreadTimeOut(Boolean.TRUE);
    }

    /**
     * 获取缓存信息
     *
     * @param cacheParam
     * @return
     */
    @Override
    public V get(K cacheParam) {
        String prefixKey = cacheParam.getPrefixKey();
        Long key = cacheParam.getKey();

        // 过滤器, 防非法穿透
        if (!this.bloomFilter(prefixKey, key)) {
            return null;
        }

        try {
            V v;
            if (null == (v = this.getFromCache(cacheParam))) {
                // 防击穿（热点key，DB存在数据），因为这里会有并发访问，所以这里需要竞争锁，优先从缓存获取数据，以保护DB
                String cacheGetLockKey = this.getCacheGetLockKey(cacheParam);
                RLock lock = redissonClient.getLock(cacheGetLockKey);
                try {
                    lock.lock(180, TimeUnit.SECONDS);
                    // 首先检查缓存是否存在, 存在则立即返回, 只有一个请求落库
                    if (null == (v = this.getFromCache(cacheParam))) {
                        v = this.getAndSet(cacheParam);
                    }
                } finally {
                    lock.unlock();
                }
            }

            // 结果一致性校验
            if (null != v) {
                this.checkResultType(cacheParam, v);
            }

            return v;
        } catch (Exception e) {
            this.logger.info("AbstractCache.getFromRedisByBucket Key = {}, 从redis中获取信息异常: {}", prefixKey + key,
                e.getMessage(), e);
            // 异常了就不考虑防击穿了, 需要检查代码问题
            return this.getAndSet(cacheParam);
        }
    }

    /**
     * 设置缓存信息
     *
     * @param cacheParam
     * @param value
     */
    @Override
    public void set(K cacheParam, V value) {
        // 异步设置缓存
        this.setAsync(cacheParam, value);
    }

    /**
     * 设置缓存信息RBucket形式，并返回信息
     *
     * @param cacheParam
     * @return
     */
    protected V getAndSet(K cacheParam) {
        try {
            if (cacheParam.getKey() == null) {
                return null;
            }

            // 缓存中不存在，则从DB获取数据
            V value = this.getData(cacheParam);

            // 异步设置缓存
            this.setAsync(cacheParam, value);

            // 因为是异步设置redis缓存，防止外部修改造成缓存数据错误
            return (V)SerializationUtils.clone((Serializable)value);
        } catch (Exception e) {
            logger.error("PrefixKey: {}, key: {}, getAndSet exception!", cacheParam.getPrefixKey(), cacheParam.getKey(),
                e);
            return null;
        }
    }

    /**
     * 布隆过滤器, 防缓存穿透(非法key, DB不存在的数据)
     *
     * @param prefixKey
     * @param key
     * @return 键值合法性, 默认全部合法, 等你来实现（BitMap）
     */
    private Boolean bloomFilter(String prefixKey, Long key) {
        // TODO 这里需要一个真的布隆过滤器(启动加载有效key, 修改同步修改key, 太麻烦了)
        return StringUtils.isNotBlank(prefixKey) && null != key;
    }

    /**
     * 获取缓存获取锁键，防击穿（热点key，DB存在数据）
     *
     * @param cacheParam
     * @return
     */
    private String getCacheGetLockKey(K cacheParam) {
        return new StringBuilder(RedisCacheConstant.CACHE_GET_LOCK_PREFIX_KEY).append(cacheParam.getPrefixKey())
            .append(cacheParam.getKey()).toString();
    }

    /**
     * 获取缓存对象
     *
     * @param cacheParam
     * @return
     */
    private V getData(K cacheParam) {
        try {
            return this.getFromDB(cacheParam);
        } catch (Exception e) {
            logger.info("AbstractCache.getData: " + cacheParam.getPrefixKey() + cacheParam.getKey() + ", Exception", e);
        }
        return null;
    }

    /**
     * 检查返回结果
     *
     * @param cacheParam
     * @param value
     */
    protected void checkResultType(K cacheParam, V value) {
        Long key = this.getKeyFromValue(value);
        // 没有一致性key, 则进行结果类型验证
        if (null == (key)) {
            // 泛型超类
            Type genericSuperclass = this.getClass();
            while (genericSuperclass instanceof Class) {
                if ((genericSuperclass = ((Class)genericSuperclass)
                    .getGenericSuperclass()) instanceof ParameterizedType) {
                    Type[] genericType = ((ParameterizedType)genericSuperclass).getActualTypeArguments();
                    Type valueGeneric = genericType[1];
                    String valueClassName = value.getClass().getName();
                    if (valueGeneric instanceof Class && !((Class)valueGeneric).getName().equals(valueClassName)) {
                        throw new ClassCastException(
                            valueClassName + " cannot be cast to " + ((Class)valueGeneric).getName());
                    } else if (valueGeneric instanceof ParameterizedType) {
                        if (!((Class)((ParameterizedType)valueGeneric).getRawType()).isInstance(value)) {
                            throw new ClassCastException(
                                valueClassName + " cannot be cast to " + ((Class)valueGeneric).getName());
                        }
                    }
                }
            }
        } else {
            // 返回结果合法检查
            if (cacheParam.getKey().compareTo(key) != 0) {
                throw new RuntimeException("The value with key " + key + " is error!");
            }
        }
    }

    /**
     * 获取返回对象键
     *
     * @param value
     * @return
     */
    protected Long getKeyFromValue(V value) {
        return null;
    }

    /**
     * <pre>
     * 设置缓存信息，RBucket
     * 缓存雪崩的问题分为两点：
     * 1、redis集群，这里我们使用的是哨兵集群
     * 2、过期时间(永久除外，无需考虑)错开，这里已经统一设置{@link cn.xyz.io.product.basic.cache.redis.constant.ICacheEnum#getTimeToLive}
     * </pre>
     *
     * @param cacheParam
     * @param value
     */
    private void setAsync(K cacheParam, V value) {
        if (null != (cacheParam.getKey())) {
            setDataToRedisCachePool.submit(new SetCacheTask(cacheParam, value, this));
        }
    }

    /**
     * 异步设置缓存任务
     */
    private class SetCacheTask implements Runnable {
        private K cacheParam;
        private V value;
        private AbstractCache<K, V> cache;

        SetCacheTask(K cacheParam, V value, AbstractCache cache) {
            this.cacheParam = cacheParam;
            this.value = value;
            this.cache = cache;
        }

        @Override
        public void run() {
            // 简单对象使用单键，可以直接获取唯一锁
            String lockKey = this.cache.getCacheSetLockKey(this.cacheParam, this.value);
            // 防重复提交
            RLock lock = redissonClient.getLock(lockKey);
            try {
                boolean tryLock = lock.tryLock(1, 180, TimeUnit.SECONDS);
                if (tryLock) {
                    logger.info("AbstractCache.SetCacheTask.run " + lockKey);
                    try {
                        if (null == (this.value)) {
                            this.value = this.cache.getData(this.cacheParam);
                        }
                        if (null != (this.value)) {
                            this.cache.setToCache(this.cacheParam, this.value);
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                logger.info("SetCacheTask exception", e);
                if (lock.isLocked()) {
                    lock.unlock();
                }
            }
        }
    }

    /**
     * 从数据库获取实体, 非自定义缓存, 实现此方法即可完成数据缓存
     *
     * @param cacheParam
     * @return
     * @throws Exception
     */
    protected abstract V getFromDB(K cacheParam) throws Exception;

    /**
     * 从缓存库中获取数据
     *
     * @param cacheParam
     * @return
     */
    protected abstract V getFromCache(K cacheParam);

    /**
     * 将缓存信息设置到缓存库当中
     *
     * @param cacheParam
     * @param value
     */
    protected abstract void setToCache(K cacheParam, V value);

    /**
     * 获取缓存设置锁键，防重复提交
     *
     * @param cacheParam
     * @param value
     * @return
     */
    protected abstract String getCacheSetLockKey(K cacheParam, V value);

}

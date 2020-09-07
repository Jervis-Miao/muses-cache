/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.cache.mycache.redis;

import cn.muses.cache.mycache.CacheParam;
import cn.muses.cache.mycache.constant.RedisCacheConstant;
import org.redisson.api.RBucket;
import org.redisson.client.codec.Codec;

/**
 * <pre>
 * Redis 对象缓存
 * RBucket简单缓存, 实现以下方法即可:
 * {@link cn.xyz.io.product.basic.cache.AbstractCache#getFromDB}
 * </pre>
 *
 * @author Jervis
 * @date 2020/3/4.
 */
public abstract class AbstractBucketRedisCache<K extends CacheParam, V> extends AbstractRedisCache<K, V> {

    /**
     * 从缓存库中获取数据
     *
     * @param key
     * @param codec
     * @return
     */
    @Override
    protected V getFromCache(K cacheParam) {
        String key = new StringBuilder(cacheParam.getPrefixKey()).append(cacheParam.getKey()).toString();
        Codec codec = cacheParam.getCodec();
        RBucket<V> bucket;
        if (null != (codec)) {
            bucket = redissonClient.getBucket(key, codec);
        } else {
            bucket = redissonClient.getBucket(key);
        }
        return bucket.get();
    }

    /**
     * 设置简易(RBucket)缓存
     *
     * @param cacheParam
     * @param value
     */
    @Override
    public void setToCache(K cacheParam, V value) {
        RBucket<V> bucket;
        if (null != (value)) {
            if (null != (cacheParam.getCodec())) {
                bucket = redissonClient.getBucket(cacheParam.getPrefixKey() + cacheParam.getKey(),
                    cacheParam.getCodec());
            } else {
                bucket = redissonClient.getBucket(cacheParam.getPrefixKey() + cacheParam.getKey());
            }
            bucket.delete();
            // 设置失效时间
            if (null != (cacheParam.getTimeToLive()) && null != (cacheParam.getTimeUnit())) {
                bucket.set(value, cacheParam.getTimeToLive(), cacheParam.getTimeUnit());
            } else {
                bucket.set(value);
            }
        }
    }

    /**
     * 获取缓存设置锁键，防重复提交
     *
     * @param cacheParam
     * @return
     */
    @Override
    protected String getCacheSetLockKey(K cacheParam, V value) {
        return new StringBuilder(RedisCacheConstant.CACHE_SET_LOCK_PREFIX_KEY).append(cacheParam.getPrefixKey())
            .append(cacheParam.getKey()).toString();
    }
}

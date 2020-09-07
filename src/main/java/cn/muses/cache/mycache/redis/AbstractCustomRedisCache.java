/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.cache.mycache.redis;

import cn.muses.cache.mycache.CacheParam;

/**
 * <pre>
 * Redis 自定义缓存, 目前限定于非简单RBucket对象缓存
 * 需同时实现如下方法:
 * {@link cn.xyz.io.product.basic.cache.AbstractCache#getFromDB}
 * {@link #getFromCache} {@link #setToCache} {@link #getCacheSetLockKey}
 * </pre>
 *
 * @author Jervis
 * @date 2020/3/4.
 */
public abstract class AbstractCustomRedisCache<K extends CacheParam, V> extends AbstractRedisCache<K, V> {

    /**
     * 从缓存库中获取数据
     *
     * @param key
     * @param codec
     * @return
     */
    @Override
    protected V getFromCache(K cacheParam) {
        logger.error("Can not getFromCache by class: " + this.getClass().getName());
        throw new RuntimeException("Can not getFromCache by class: " + this.getClass().getName());
    }

    /**
     * 将数据设置到缓存当中
     *
     * @param cacheParam
     * @param value
     */
    @Override
    public void setToCache(K cacheParam, V value) {
        logger.error("Can not setToCache by class: " + this.getClass().getName());
        throw new RuntimeException("Can not setToCache by class: " + this.getClass().getName());
    }

    /**
     * 获取缓存设置锁键值，防重复提交
     *
     * @param cacheParam
     * @param value
     * @return
     */
    @Override
    protected String getCacheSetLockKey(K cacheParam, V value) {
        logger.error("Can not getCacheSetLockKey by class: " + this.getClass().getName());
        throw new RuntimeException("Can not getCacheSetLockKey by class: " + this.getClass().getName());
    }

}

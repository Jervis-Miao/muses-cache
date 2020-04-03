/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.cache.redis;

import com.muses.cache.AbstractCache;
import com.muses.cache.CacheParam;
import com.muses.cache.IClient;

/**
 * <pre>
 * Redis缓存
 * 暂时就不实现客户端了(转嫁给redisson)
 * @see org.redisson.api.RedissonClient {@link AbstractCache#redissonClient}
 * </pre>
 *
 * @author Jervis
 * @date 2020/3/4.
 */
public abstract class AbstractRedisCache<K extends CacheParam, V> extends AbstractCache<K, V> implements IClient {

    @Override
    public void insert(Object key, Object value) {}

    @Override
    public boolean delete(Object key) {
        return true;
    }

    @Override
    public Object update(Object key, Object value) {
        return null;
    }

    @Override
    public Object select(Object key) {
        return null;
    }
}

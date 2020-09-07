/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.cache.mycache.redis;

import cn.muses.cache.mycache.AbstractCache;
import cn.muses.cache.mycache.CacheParam;
import cn.muses.cache.mycache.IClient;

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

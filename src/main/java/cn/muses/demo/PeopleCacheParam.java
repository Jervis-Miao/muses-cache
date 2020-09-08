/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.demo;

import cn.muses.cache.mycache.AbstractCache;
import cn.muses.cache.mycache.CacheParam;
import cn.muses.cache.mycache.CodecFactory;
import cn.muses.cache.mycache.constant.RedisCacheConstant;
import cn.muses.cache.mycache.redis.AbstractBucketRedisCache;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
public class PeopleCacheParam extends CacheParam {
    private static final RedisCacheConstant.CACHE_PARAM PEOPLE_PARAM = RedisCacheConstant.CACHE_PARAM.PEOPLE_CACHE;

    public PeopleCacheParam() {
        super(PEOPLE_PARAM.prefixKey(), null, CodecFactory.getRedissonCodecInstance(PEOPLE_PARAM.codecClass()),
            PEOPLE_PARAM.timeToLive(), PEOPLE_PARAM.timeUnit());
    }

    public PeopleCacheParam(Long key) {
        super(PEOPLE_PARAM.prefixKey(), key, CodecFactory.getRedissonCodecInstance(PEOPLE_PARAM.codecClass()),
            PEOPLE_PARAM.timeToLive(), PEOPLE_PARAM.timeUnit());
    }

}

/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.demo;

import com.muses.cache.mycache.CacheParam;
import com.muses.cache.mycache.CodecFactory;
import com.muses.cache.mycache.constant.RedisCacheConstant;

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

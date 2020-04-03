/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.demo;

import com.muses.cache.redis.AbstractBucketRedisCache;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
public class PeopleCache extends AbstractBucketRedisCache<PeopleCacheParam, PeopleDTO> {

    @Override
    protected PeopleDTO getFromDB(PeopleCacheParam cacheParam) throws Exception {
        return new PeopleDTO();
    }
}

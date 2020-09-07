/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.demo;

import cn.muses.cache.mycache.redis.AbstractBucketRedisCache;

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

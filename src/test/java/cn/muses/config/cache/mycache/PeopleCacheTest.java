/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.config.cache.mycache;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;

import cn.muses.cache.mycache.AbstractCache;
import cn.muses.cache.mycache.redis.AbstractBucketRedisCache;
import cn.muses.config.JunitTest;
import cn.muses.demo.PeopleCache;
import cn.muses.demo.PeopleCacheParam;
import cn.muses.demo.PeopleDTO;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
public class PeopleCacheTest extends JunitTest {

    @Resource
    private PeopleCache peopleCache;

    @Test
    public void testCache1() {
        PeopleDTO peopleDTO = peopleCache.get(new PeopleCacheParam(123L));
        System.out.println("我今天遇到一个人, " + peopleDTO);
    }

    @Test
    public void testCache2() {
        AbstractCache<PeopleCacheParam, PeopleDTO> cache = new AbstractBucketRedisCache<PeopleCacheParam, PeopleDTO>() {
            @Override
            protected PeopleDTO getFromDB(PeopleCacheParam cacheParam) throws Exception {
                return new PeopleDTO();
            }
        };

        PeopleDTO peopleDTO = cache.get(new PeopleCacheParam(123L));
        System.out.println("我今天遇到一个人, " + peopleDTO);
    }
}

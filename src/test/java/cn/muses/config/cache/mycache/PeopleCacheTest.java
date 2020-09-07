/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.config.cache.mycache;

import cn.muses.config.JunitTest;
import cn.muses.demo.PeopleCache;
import cn.muses.demo.PeopleCacheParam;
import cn.muses.demo.PeopleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
public class PeopleCacheTest extends JunitTest {

    @Autowired
    private PeopleCache peopleCache;

    @Test
    public void testCacheGet() {
        PeopleDTO peopleDTO = peopleCache.get(new PeopleCacheParam(123L));
        System.out.println("我今天遇到一个人, " + peopleDTO);
    }
}

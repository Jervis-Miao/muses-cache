/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.config.cache;

import com.muses.config.JunitTest;
import com.muses.demo.PeopleCache;
import com.muses.demo.PeopleCacheParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author miaoqiang
 * @date 2020/4/3.
 */
public class PeopleCacheTest extends JunitTest {

    @Autowired
    private PeopleCache peopleCache;

    @Test
    public void testCacheGet() {
        System.out.println("我今天遇到一个人, " + peopleCache.get(new PeopleCacheParam(123L)));
    }
}
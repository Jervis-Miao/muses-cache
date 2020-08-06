/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.config.cache.jetcache;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.muses.config.JunitTest;
import com.muses.demo.PeopleDTO;

/**
 * @author miaoqiang
 * @date 2020/8/6.
 */
public class JetCacheTest extends JunitTest {

    @CreateCache(cacheType = CacheType.LOCAL, expire = 2)
    private Cache<String, PeopleDTO> peopleCache;

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            PeopleDTO p = peopleCache.computeIfAbsent("test", (k) -> {
                return new PeopleDTO();
            });
            System.out.println(String.format("i: %s ==> Cache hash: %s", i, peopleCache.hashCode()));
            System.out.println(String.format("i: %s ==> PeopleDTO hash: %s, %s", i, p.hashCode(), p));
            Thread.sleep(1000);
        }
    }

}

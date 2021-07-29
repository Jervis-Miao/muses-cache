/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.config.redisson;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import cn.muses.config.JunitTest;

/**
 * @author miaoqiang
 * @date 2020/4/26.
 */
public class RedissonTest extends JunitTest {

    @Autowired
    private RedissonClient redisson;

    @Test
    public void RScoredSortedSetTest() {
        RScoredSortedSet<String> sortedSet = redisson.getScoredSortedSet("RScoredSortedSetTest");
        RScoredSortedSet<String> sortedSet1 = redisson.getScoredSortedSet("RScoredSortedSetTest:1");
        System.out.println(sortedSet.add(300, "test"));
        System.out.println(sortedSet1.add(300, "test"));
    }

    @Test
    public void test1() throws InterruptedException {
        final RBucket<Object> bucket = redisson.getBucket("key");
        bucket.set("key", 10, TimeUnit.SECONDS);
        System.out.println(bucket.remainTimeToLive());
    }
}

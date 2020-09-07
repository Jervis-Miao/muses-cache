/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.config.cache.jetcache;

import java.util.concurrent.TimeUnit;

import cn.muses.config.JunitTest;
import cn.muses.demo.PeopleDTO;
import org.junit.jupiter.api.Test;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;

/**
 * @author miaoqiang
 * @date 2020/8/6.
 */
public class JetCacheTest extends JunitTest {

    @CreateCache(name = "peopleCache", cacheType = CacheType.LOCAL, expire = 20000, timeUnit = TimeUnit.SECONDS,
        localLimit = Integer.MAX_VALUE)
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

    @Test
    public void test1() throws InterruptedException {
        String key = "test";
        final Thread thread1 = getThread(key, 1000);
        // final Thread thread2 = getThread(key);
        thread1.start();
        // thread2.start();
        thread1.join();
        // thread2.join();

    }

    private Thread getThread(String key, long sleep) {
        return new Thread(() -> {
            System.out.println(Thread.currentThread().getId() + "开始");
            for (int i = 0; i < 100; i++) {
                PeopleDTO peopleDTO;
                if ((peopleDTO = peopleCache.get(key)) == null) {
                    try {
                        Thread.sleep(sleep);
                        peopleCache.putIfAbsent(key, new PeopleDTO());
                        peopleDTO = peopleCache.get(key);
                        System.out.println(Thread.currentThread().getId() + " 重新生成: " + peopleDTO.hashCode());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out
                        .println(String.format("%s 获取成功: %s", Thread.currentThread().getId(), peopleDTO.hashCode()));
                }
            }
        });
    }

}

/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.cache;

import cn.muses.cache.config.mycache.EnableMusesCache;
import cn.muses.cache.config.jetcache.EnableMusesJetCacheLocal;
import cn.muses.cache.demo.PeopleCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
@SpringBootApplication
@EnableMusesCache({PeopleCache.class})
@EnableMusesJetCacheLocal
public class MusesCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusesCacheApplication.class, args);
    }

}

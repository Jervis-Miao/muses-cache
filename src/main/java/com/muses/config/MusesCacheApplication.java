/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.muses.config.spring.jetcache.EnableMusesJetCacheLocal;
import com.muses.config.spring.mycache.EnableMusesCache;
import com.muses.demo.PeopleCache;

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

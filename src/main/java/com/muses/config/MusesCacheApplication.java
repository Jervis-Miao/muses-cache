/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.muses.config.spring.EnableMusesCache;
import com.muses.demo.PeopleCache;

/**
 * @author miaoqiang
 * @date 2020/4/3.
 */
@SpringBootApplication
@EnableMusesCache({PeopleCache.class})
public class MusesCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusesCacheApplication.class, args);
    }

}

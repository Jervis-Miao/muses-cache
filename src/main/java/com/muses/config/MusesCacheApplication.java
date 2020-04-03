/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.config;

import com.muses.demo.PeopleCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.muses.config.spring.EnableMusesCache;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

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

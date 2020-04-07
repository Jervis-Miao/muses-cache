/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@EnableAsync(proxyTargetClass = true)
@EnableAspectJAutoProxy(exposeProxy = true)
public class JunitTest {
    @BeforeAll
    static void setUp() {
    }
}

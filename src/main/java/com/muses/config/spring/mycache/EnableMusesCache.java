/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.config.spring.mycache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import com.muses.cache.mycache.AbstractCache;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MusesCacheConfiguration.class)
public @interface EnableMusesCache {

    /**
     * @return The cache class(es) should be imported
     */
    @AliasFor("cacheClasses")
    Class<? extends AbstractCache>[] value() default {};

    /**
     * @return The cache class(es) should be imported
     */
    @AliasFor("value")
    Class<? extends AbstractCache>[] cacheClasses() default {};

    /**
     * @return resource configuration class
     */
    ResourceConfiguration resource() default ResourceConfiguration.REDISSON_SENTINEL_REDIS;

    /**
     * 数据源配置
     */
    public enum ResourceConfiguration {
        /**
         * redisson redis哨兵数据源
         */
        REDISSON_SENTINEL_REDIS,

        /**
         * redisson redis单机数据源
         */
        REDISSON_SINGLE_REDIS;
    }
}

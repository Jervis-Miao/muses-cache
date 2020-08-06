/*
Copyright 2019 All rights reserved.
 */

package com.muses.config.spring.jetcache;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;

/**
 * <code>jetcache</code>官方地址：{@link https://github.com/alibaba/jetcache/wiki/Home_CN}
 *
 * @author miaoqiang
 * @date 2020/8/6.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.muses.cache")
public @interface EnableMusesJetCacheLocal {
}

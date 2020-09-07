/*
Copyright 2019 All rights reserved.
 */

package cn.muses.config.spring.jetcache;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author miaoqiang
 * @date 2020/8/6.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JetCacheConfiguration.class)
public @interface EnableMusesJetCacheSdk {
}

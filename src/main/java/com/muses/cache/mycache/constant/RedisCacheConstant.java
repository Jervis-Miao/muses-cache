/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.cache.mycache.constant;

import java.util.concurrent.TimeUnit;

import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonJacksonCodec;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
public interface RedisCacheConstant {

    public static final String LOCK_PREFIX_KEY = "lock:";
    public static final String CACHE_GET_LOCK_PREFIX_KEY = LOCK_PREFIX_KEY + "cache:get:";
    public static final String CACHE_SET_LOCK_PREFIX_KEY = LOCK_PREFIX_KEY + "cache:set:";

    /**
     * 缓存参数常量
     */
    public enum CACHE_PARAM implements ICacheEnum {
        /**
         * 人
         */
        PEOPLE_CACHE("people:cache:", 10L, TimeUnit.MINUTES, JsonJacksonCodec.class);

        /**
         * 缓存前缀
         */
        private final String prefixKey;

        /**
         * 超时时间
         */
        private final Long timeToLive;

        /**
         * 超时时间单位
         */
        private final TimeUnit timeUnit;

        /**
         * 序列化类 {@link Codec}
         */
        private final Class<?> codecClass;

        CACHE_PARAM(String prefixKey, Long timeToLive, TimeUnit timeUnit, Class<?> codecClass) {
            this.prefixKey = prefixKey;
            this.timeToLive = timeToLive;
            this.timeUnit = timeUnit;
            this.codecClass = codecClass;
        }

        @Override
        public String prefixKey() {
            return this.prefixKey;
        }

        @Override
        public Long timeToLive() {
            return this.timeToLive;
        }

        @Override
        public TimeUnit timeUnit() {
            return this.timeUnit;
        }

        public Class codecClass() {
            return this.codecClass;
        }
    }

}

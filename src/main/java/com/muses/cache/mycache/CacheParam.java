/*
 * Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package com.muses.cache.mycache;

import java.util.concurrent.TimeUnit;

import org.redisson.client.codec.Codec;

/**
 * @author Jervis
 * @date 2018/10/17.
 */
public class CacheParam {

    /**
     * 前缀
     */
    private String prefixKey;

    /**
     * 主键
     */
    private Long key;

    /**
     * 序列化方式
     */
    private Codec codec;

    /**
     * 失效时间
     */
    private Long timeToLive;

    /**
     * 失效时间单位
     */
    private TimeUnit timeUnit;

    public CacheParam() {}

    public CacheParam(String prefixKey, Long key) {
        this(prefixKey, key, null);
    }

    public CacheParam(String prefixKey, Long key, Codec codec) {
        this(prefixKey, key, codec, null, null);
    }

    public CacheParam(String prefixKey, Long key, Long timeToLive, TimeUnit timeUnit) {
        this(prefixKey, key, null, timeToLive, timeUnit);
    }

    public CacheParam(String prefixKey, Long key, Codec codec, Long timeToLive, TimeUnit timeUnit) {
        this.prefixKey = prefixKey;
        this.key = key;
        this.codec = codec;
        this.timeToLive = timeToLive;
        this.timeUnit = timeUnit;
    }

    public String getPrefixKey() {
        return prefixKey;
    }

    public void setPrefixKey(String prefixKey) {
        this.prefixKey = prefixKey;
    }

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Codec getCodec() {
        return codec;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }

    public Long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}

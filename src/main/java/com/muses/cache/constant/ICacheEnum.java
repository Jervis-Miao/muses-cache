/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.cache.constant;

import java.util.concurrent.TimeUnit;

/**
 * @author miaoqiang
 * @date 2019/12/20.
 */
public interface ICacheEnum {

    /**
     * 获取缓存key
     * 
     * @return
     */
    String prefixKey();

    /**
     * 获取缓存存活时间
     *
     * @return
     */
    Long timeToLive();

    /**
     * 获取缓存存活时间单位
     *
     * @return
     */
    TimeUnit timeUnit();

}
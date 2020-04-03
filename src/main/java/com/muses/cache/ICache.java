/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.cache;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
public interface ICache<K, V> {
    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public V get(K key);

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public void set(K key, V value);

    /**
     * 删除缓存
     *
     * @param key
     */
    default void remove(K key) {}
}

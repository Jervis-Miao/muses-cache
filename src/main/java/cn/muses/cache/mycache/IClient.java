/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.cache.mycache;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
public interface IClient<K, V> {

    /**
     * 增
     *
     * @param key
     * @param value
     */
    public void insert(K key, V value);

    /**
     * 删
     *
     * @param key
     * @return
     */
    public boolean delete(K key);

    /**
     * 改
     *
     * @param key
     * @param value
     * @return
     */
    public V update(K key, V value);

    /**
     * 查
     *
     * @param key
     * @return
     */
    public V select(K key);

}

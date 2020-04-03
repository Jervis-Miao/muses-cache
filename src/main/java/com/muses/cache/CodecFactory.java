/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.cache;

import org.redisson.client.codec.Codec;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
public class CodecFactory {

    /**
     * 获取redisson序列化实例 <br/>
     * 如果有spring上下文，这里可以从spring获取 <br/>
     * Codec codec = SpringContextUtils.getBean(PEOPLE_PARAM.codecClass());
     *
     * @param clazz
     * @return
     */
    public static Codec getRedissonCodecInstance(Class<Codec> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}

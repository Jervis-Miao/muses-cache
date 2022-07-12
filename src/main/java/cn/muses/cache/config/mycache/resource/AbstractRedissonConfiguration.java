/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.cache.config.mycache.resource;

import org.redisson.client.codec.StringCodec;
import org.redisson.codec.FstCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.SerializationCodec;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
public abstract class AbstractRedissonConfiguration extends AbstractRedisConfiguration {

    @Bean
    @ConditionalOnMissingBean(FstCodec.class)
    public FstCodec fstCodec() {
        return new FstCodec();
    }

    @Bean
    @ConditionalOnMissingBean(JsonJacksonCodec.class)
    public JsonJacksonCodec jsonJacksonCodec() {
        return new JsonJacksonCodec();
    }

    @Bean
    @ConditionalOnMissingBean(SerializationCodec.class)
    public SerializationCodec jdkCodec() {
        return new SerializationCodec();
    }

    @Bean
    @ConditionalOnMissingBean(StringCodec.class)
    public StringCodec StringCodec() {
        return new StringCodec();
    }

}

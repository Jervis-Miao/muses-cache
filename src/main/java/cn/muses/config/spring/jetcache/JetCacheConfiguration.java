/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.config.spring.jetcache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.muses.cache.constants.RedisCacheConst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.alicp.jetcache.CacheBuilder;
import com.alicp.jetcache.anno.CacheConsts;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.alicp.jetcache.embedded.CaffeineCacheBuilder;
import com.alicp.jetcache.embedded.EmbeddedCacheBuilder;
import com.alicp.jetcache.redis.RedisCacheBuilder;
import com.alicp.jetcache.support.FastjsonKeyConvertor;
import com.alicp.jetcache.support.KryoValueDecoder;
import com.alicp.jetcache.support.KryoValueEncoder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

/**
 * @author miaoqiang
 * @date 2020/8/6.
 */
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.muses.cache")
public class JetCacheConfiguration {

    @Value("${redis.sentinel.ser1.host}")
    private String ser1Host;
    @Value("${redis.sentinel.ser2.host}")
    private String ser2Host;
    @Value("${redis.sentinel.ser3.host}")
    private String ser3Host;
    @Value("${redis.sentinel.ser1.port}")
    private String ser1Port;
    @Value("${redis.sentinel.ser2.port}")
    private String ser2Port;
    @Value("${redis.sentinel.ser3.port}")
    private String ser3Port;
    @Value("${redis.sentinel.lowRel.masterName}")
    private String lowRelMasterName;

    @Bean
    public Pool<Jedis> pool() {
        Set<String> sentinels = new HashSet<>();
        sentinels.add(ser1Host + ":" + ser1Port);
        sentinels.add(ser2Host + ":" + ser2Port);
        sentinels.add(ser3Host + ":" + ser3Port);
        return new JedisSentinelPool(lowRelMasterName, sentinels);
    }

    @Bean
    public SpringConfigProvider springConfigProvider() {
        return new SpringConfigProvider();
    }

    @Bean
    public GlobalCacheConfig config(SpringConfigProvider configProvider, Pool<Jedis> pool) {
        Map<String, CacheBuilder> localBuilders = new HashMap<>(1);
        // 本地缓存配置
        EmbeddedCacheBuilder localBuilder =
            CaffeineCacheBuilder.createCaffeineCacheBuilder().keyConvertor(FastjsonKeyConvertor.INSTANCE);
        localBuilder.setLimit(100);
        localBuilder.setCacheNullValue(false);
        localBuilder.setExpireAfterAccessInMillis(60 * 1000);
        localBuilders.put(CacheConsts.DEFAULT_AREA, localBuilder);
        // redis分布式缓存配置
        Map<String, CacheBuilder> remoteBuilders = new HashMap<>(1);
        RedisCacheBuilder remoteCacheBuilder =
            RedisCacheBuilder.createRedisCacheBuilder().keyConvertor(FastjsonKeyConvertor.INSTANCE)
                .valueEncoder(KryoValueEncoder.INSTANCE).valueDecoder(KryoValueDecoder.INSTANCE).jedisPool(pool);
        remoteCacheBuilder.setCacheNullValue(true);
        remoteCacheBuilder.setExpireAfterWriteInMillis(4 * 60 * 60 * 1000);
        remoteCacheBuilder.setKeyPrefix(RedisCacheConst.MUSES_CACHE_PREFIX);
        remoteBuilders.put(CacheConsts.DEFAULT_AREA, remoteCacheBuilder);
        // 全局配置
        GlobalCacheConfig globalCacheConfig = new GlobalCacheConfig();
        globalCacheConfig.setConfigProvider(configProvider);
        globalCacheConfig.setLocalCacheBuilders(localBuilders);
        globalCacheConfig.setRemoteCacheBuilders(remoteBuilders);
        globalCacheConfig.setStatIntervalMinutes(15);
        globalCacheConfig.setAreaInCacheName(false);
        globalCacheConfig.setHiddenPackages(new String[] {""});
        return globalCacheConfig;
    }
}

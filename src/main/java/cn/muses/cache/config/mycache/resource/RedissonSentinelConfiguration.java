/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.cache.config.mycache.resource;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SubscriptionMode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * 哨兵redis
 *
 * @author Jervis
 * @date 2020/4/3.
 */
public class RedissonSentinelConfiguration extends AbstractRedissonConfiguration {

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
    @Value("${redis.sentinel.default.readMode:MASTER}")
    private ReadMode readMode;
    @Value("${redis.sentinel.default.subscriptionMode:MASTER}")
    private SubscriptionMode subscriptionMode;
    @Value("${redis.sentinel.default.subscriptionConnectionMinimumIdleSize:1}")
    private int subscriptionConnectionMinimumIdleSize;
    @Value("${redis.sentinel.default.subscriptionConnectionPoolSize:50}")
    private int subscriptionConnectionPoolSize;
    @Value("${redis.sentinel.default.slaveConnectionMinimumIdleSize:0}")
    private int slaveConnectionMinimumIdleSize;
    @Value("${redis.sentinel.default.slaveConnectionPoolSize:0}")
    private int slaveConnectionPoolSize;
    @Value("${redis.sentinel.default.masterConnectionMinimumIdleSize:10}")
    private int masterConnectionMinimumIdleSize;
    @Value("${redis.sentinel.default.masterConnectionPoolSize:64}")
    private int masterConnectionPoolSize;
    @Value("${redis.sentinel.default.idleConnectionTimeout:10000}")
    private int idleConnectionTimeout;
    @Value("${redis.sentinel.default.connectTimeout:10000}")
    private int connectTimeout;
    @Value("${redis.sentinel.default.timeout:3000}")
    private int timeout;
    @Value("${redis.sentinel.default.retryAttempts:3}")
    private int retryAttempts;
    @Value("${redis.sentinel.default.retryInterval:500}")
    private int retryInterval;
    @Value("${redis.sentinel.default.failedSlaveReconnectionInterval:3000}")
    private int failedSlaveReconnectionInterval;
    @Value("${redis.sentinel.default.failedSlaveCheckInterval:60000}")
    private int failedSlaveCheckInterval;
    @Value("${redis.sentinel.default.database:0}")
    private int database;
    @Value("${redis.sentinel.default.subscriptionsPerConnection:5}")
    private int subscriptionsPerConnection;

    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient(@Qualifier("fstCodec") Codec codec) {
        Config config = new Config();
        SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
        sentinelServersConfig.setMasterName(lowRelMasterName).addSentinelAddress("redis://" + ser1Host + ":" + ser1Port,
            "redis://" + ser2Host + ":" + ser2Port, "redis://" + ser3Host + ":" + ser3Port);
        sentinelServersConfig.setReadMode(readMode);
        sentinelServersConfig.setSubscriptionMode(subscriptionMode);
        sentinelServersConfig.setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize);
        sentinelServersConfig.setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize);
        sentinelServersConfig.setSlaveConnectionMinimumIdleSize(slaveConnectionMinimumIdleSize);
        sentinelServersConfig.setSlaveConnectionPoolSize(slaveConnectionPoolSize);
        sentinelServersConfig.setMasterConnectionMinimumIdleSize(masterConnectionMinimumIdleSize);
        sentinelServersConfig.setMasterConnectionPoolSize(masterConnectionPoolSize);
        sentinelServersConfig.setIdleConnectionTimeout(idleConnectionTimeout);
        sentinelServersConfig.setConnectTimeout(connectTimeout);
        sentinelServersConfig.setTimeout(timeout);
        sentinelServersConfig.setRetryAttempts(retryAttempts);
        sentinelServersConfig.setRetryInterval(retryInterval);
        sentinelServersConfig.setFailedSlaveReconnectionInterval(failedSlaveCheckInterval);
        sentinelServersConfig.setFailedSlaveCheckInterval(failedSlaveCheckInterval);
        sentinelServersConfig.setDatabase(database);
        sentinelServersConfig.setSubscriptionsPerConnection(subscriptionsPerConnection);
        /**
         * 序列化方式 默认采用fst {@link AbstractRedissonConfiguration#fstCodec()}
         */
        config.setCodec(codec);
        return Redisson.create(config);
    }

}

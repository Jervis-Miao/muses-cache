/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.config.spring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.muses.config.spring.EnableMusesCache.ResourceConfiguration;
import com.muses.config.spring.resource.RedissonSentinelConfiguration;
import com.muses.config.spring.resource.RedissonSingleConfigure;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
public class MusesCacheConfiguration implements ImportSelector {

    /**
     * 注入缓存对象到spring上下文
     *
     * @param metadata
     * @return
     */
    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        // 获取缓存bean, 构建缓存对象
        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableMusesCache.class.getName());
        List<String> names = this.getCacheBeans(attributes);
        names.add(this.getResourceConfigurationBean(attributes));

        return names.toArray(new String[names.size()]);
    }

    /**
     * 获取缓存bean的class名称集合
     *
     * @param attributes
     * @return
     */
    private List<String> getCacheBeans(Map<String, Object> attributes) {
        Class[] cacheClasses = (Class[])attributes.get("cacheClasses");
        if (ArrayUtils.isEmpty(cacheClasses)) {
            return new ArrayList<>(2);
        }
        return Arrays.stream(cacheClasses).map(cacheClass -> (cacheClass.getName())).collect(Collectors.toList());
    }

    /**
     * 获取缓存数据源class名称
     * 
     * @param attributes
     * @return
     */
    private String getResourceConfigurationBean(Map<String, Object> attributes) {
        ResourceConfiguration resource = (ResourceConfiguration)attributes.get("resource");
        switch (resource) {
            case REDISSON_SINGLE_REDIS:
                return RedissonSingleConfigure.class.getName();
            default:
                return RedissonSentinelConfiguration.class.getName();
        }
    }
}

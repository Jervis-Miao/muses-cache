/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.cache.mycache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.redisson.client.codec.Codec;

/**
 * @author Jervis
 * @date 2020/4/3.
 */
public class CodecFactory {
	/**
	 * 序列化实例
	 */
	private static final Map<Class<? extends Codec>, Codec>	CODEC	= new ConcurrentHashMap<>(8);

	/**
	 * 同步操作锁
	 */
	private static final Lock								LOCK	= new ReentrantLock();

	/**
	 * 获取redisson序列化实例
	 *
	 * @param clazz
	 * @return
	 */
	public static Codec getRedissonCodecInstance(Class<Codec> clazz) {
		Codec codec;
		// 检查缓存
		if (null == (codec = CODEC.get(clazz))) {
			// 阻塞
			LOCK.lock();
			try {
				// 继续检查缓存
				if (null == (codec = CODEC.get(clazz))) {
					try {
						/**
						 * 不存在缓存, 创建实例<br/>
						 * 如果有spring上下文，这里可以从spring获取, <code>codec = SpringContextUtils.getBean(clazz);</code>
						 */
						codec = clazz.newInstance();
						CODEC.put(clazz, codec);
					} catch (InstantiationException e) {
						e.printStackTrace();
						codec = null;
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						codec = null;
					}
				}
			} finally {
				LOCK.unlock();
			}
		}
		return codec;
	}

}

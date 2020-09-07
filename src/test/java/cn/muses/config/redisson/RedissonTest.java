/*
Copyright 2019 All rights reserved.
 */

package cn.muses.config.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import cn.muses.config.JunitTest;

/**
 * @author miaoqiang
 * @date 2020/4/26.
 */
public class RedissonTest extends JunitTest {

	@Autowired
	private RedissonClient redisson;

	@Test
	public void RScoredSortedSetTest() {
		RScoredSortedSet<String> sortedSet = redisson.getScoredSortedSet("RScoredSortedSetTest");
		RScoredSortedSet<String> sortedSet1 = redisson.getScoredSortedSet("RScoredSortedSetTest:1");
		System.out.println(sortedSet.add(300, "test"));
		System.out.println(sortedSet1.add(300, "test"));
	}
}

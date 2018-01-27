package com.taotao.sso.service.RedisImpl;

import redis.clients.jedis.JedisCluster;

public class Cluster implements RedisUtils {

		private JedisCluster jedisCluster;
	@Override
	public void set(String key, String value) {
		// TODO Auto-generated method stub
		jedisCluster.set(key, value);
	}

	@Override
	public void set(String key, String value, Integer time) {
		// TODO Auto-generated method stub
		this.jedisCluster.set(key, value);
			expire(key, time);
	}

	@Override
	public void del(String key) {
		// TODO Auto-generated method stub
		jedisCluster.del(key);
	}

	@Override
	public void expire(String key, Integer seconds) {
		// TODO Auto-generated method stub
		jedisCluster.expire(key, seconds);
	}

	@Override
	public long incr(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.incr(key);
	}

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		String string = jedisCluster.get(key);
		return string;
	}

}

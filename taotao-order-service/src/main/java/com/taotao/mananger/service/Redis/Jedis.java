package com.taotao.mananger.service.Redis;

import redis.clients.jedis.JedisPool;

public class Jedis implements RedisUtils{
	
	private JedisPool jedisPool;
	@Override
	public void set(String key, String value) {
		redis.clients.jedis.Jedis jedis = jedisPool.getResource();
		jedis.set(key, value);
		
	}

	@Override
	public void set(String key, String value, Integer time) {
		// TODO Auto-generated method stub
		redis.clients.jedis.Jedis jedis = jedisPool.getResource();
		jedis.set(key,value);
		jedis.expire(key, time);
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@Override
	public void del(String key) {
		// TODO Auto-generated method stub\
		redis.clients.jedis.Jedis jedis = jedisPool.getResource();
		jedis.del(key);
	}

	@Override
	public void expire(String key, Integer seconds) {
		// TODO Auto-generated method stub
		redis.clients.jedis.Jedis jedis = jedisPool.getResource();
		jedis.expire(key, seconds);
	}

	@Override
	public long incr(String key) {
		// TODO Auto-generated method stub
		redis.clients.jedis.Jedis jedis = jedisPool.getResource();
		long incr = jedis.incr(key);
		return incr;
	}

	


	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		redis.clients.jedis.Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		return string;
	}

}

package com.taotao.sso.service.RedisImpl;

public interface RedisUtils {
	
	public void set(String key,String value);
	
	public void set(String key,String value ,Integer time);
	
	public void del(String key);
	
	public void expire(String key,Integer seconds);
	
	public long incr(String key);
	
	public String get(String key);

}

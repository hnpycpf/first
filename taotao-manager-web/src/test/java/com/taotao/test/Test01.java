package com.taotao.test;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.TableGenerator;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class Test01 {
	@Test
	public void me(){
		Set<HostAndPort> set=new HashSet<>();
		set.add(new HostAndPort("192.168.37.161", 7001));
		set.add(new HostAndPort("192.168.37.161", 7002));
		set.add(new HostAndPort("192.168.37.161", 7003));
		set.add(new HostAndPort("192.168.37.161", 7004));
		set.add(new HostAndPort("192.168.37.161", 7005));
		set.add(new HostAndPort("192.168.37.161", 7006));
		
		JedisCluster cluster=new JedisCluster(set);
		cluster.set("zhangsan", "123");
		
		System.out.println(cluster.get("zhangsan"));
		
	}

}

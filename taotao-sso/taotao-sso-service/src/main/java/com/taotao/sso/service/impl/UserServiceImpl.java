package com.taotao.sso.service.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.validator.ValidateWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.mapper.Mapper;
import com.taotao.manager.mapper.UserMapper;
import com.taotao.manager.pojo.User;
import com.taotao.sso.service.UserService;
import com.taotao.sso.service.RedisImpl.Cluster;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private Mapper<User> userMapper;
	@Autowired
	private Cluster redisCluster;
	@Value("${TAOTAO.TICKETKEY}")
	private String TICKETKEY;
	private ObjectMapper object=new ObjectMapper();
	@Override
	public boolean check(String username, Integer type) {
		// TODO Auto-generated method stub
		User user=new User();
		switch (type) {
		case 1:
			user.setUsername(username);;
			break;
		case 2:
			user.setPhone(username);
			break;
		case 3:
			user.setEmail(username);
			break;
		default:
			break;
		}
		int selectCount = userMapper.selectCount(user);
		return selectCount==0;
	}

	@Override
	public void doRegister(User user) {
		Date date = new Date();
		user.setCreated(date);
		user.setUpdated(date);
		user.setPassword(new DigestUtils().md5Hex(user.getPassword()));
		userMapper.insert(user);
		
	}

	@Override
	public String doLogin(User user) throws JsonProcessingException {
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		User selectUser = userMapper.selectOne(user);
		String ticketKey = null;
		if(selectUser!=null){
			long incr = redisCluster.incr("incrKey");
			String ticket=""+incr+selectUser.getId();
			ticketKey=TICKETKEY+ticket;
			redisCluster.set(ticketKey, object.writeValueAsString(selectUser),60*60);
		}
		
		return ticketKey;
	}

	@Override
	public User getUser(String ticket) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		String string = redisCluster.get(ticket);
		User user = object.readValue(string, User.class);
		return user;
	}

	@Override
	public void reloadTime(String ticket) {
		redisCluster.expire(ticket, 60*60);
	}
}

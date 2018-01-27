package com.taotao.sso.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.taotao.manager.pojo.User;

public interface UserService {

	boolean check(String username, Integer type);

	void doRegister(User user);

	String doLogin(User user) throws JsonProcessingException;

	User getUser(String ticket) throws JsonParseException, JsonMappingException, IOException;

	void reloadTime(String ticket);

	

	
	
	

}

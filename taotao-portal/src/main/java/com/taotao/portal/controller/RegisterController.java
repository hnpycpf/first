package com.taotao.portal.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manager.pojo.User;
import com.taotao.sso.service.UserService;

import utils.CookieUtils;

@Controller
@RequestMapping("/user")
public class RegisterController {
	private ObjectMapper object=new ObjectMapper();
	@Autowired
	private UserService userService;
	@Value("${COOKIE_NAME}")
	private String cookieName;
	@RequestMapping("/doRegister")
	@ResponseBody
	public String doRegister(User user){
		try {
			userService.doRegister(user);
			HashMap<String, String> hashMap = new HashMap<>();
			hashMap.put("status", "200");
			String string = object.writeValueAsString(hashMap);
			return string;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	@ResponseBody
	@RequestMapping("/doLogin")
	public String doLogin(User user,HttpServletRequest request,HttpServletResponse response){
		try {
			String ticketKey=userService.doLogin(user);
			if(ticketKey!=null){
				CookieUtils.setCookie(request, response, cookieName,ticketKey ,60*60*24, true);
				HashMap<String, String> hashMap = new HashMap<>();
				hashMap.put("status", "200");
				return object.writeValueAsString(hashMap);
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	}
	@RequestMapping(value="{ticket}",method=RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable String ticket){
		try {
			User user=userService.getUser(ticket);
			userService.reloadTime(ticket);
			return ResponseEntity.ok(user);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}

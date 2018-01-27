package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.sso.service.UserService;

@Controller
@RequestMapping("/check")
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping(value="/{username}/{type}")
	@ResponseBody
	public String check(@PathVariable String username,@PathVariable Integer type,HttpServletRequest request){
		
		boolean flag=userService.check(username,type);
		String attribute =request.getParameter("callback").toString();
		String callback;
		if(attribute!=null){
			if(flag){
				callback=attribute+"("+flag+")";
			}
			else{
				callback=attribute+"("+flag+")";
			}
		}
		else{
			callback=flag+"";
		}
		return callback;
		
	}
	

}

package com.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.manager.pojo.Content;
import com.taotao.manager.service.ContentService;

@Controller
@RequestMapping("/page")
public class pageController {
	@Autowired
	private ContentService contentService;
	@RequestMapping("/{name}")
	public String contentPage(@PathVariable("name")String name){
		
		return name;
		
	}
	
}

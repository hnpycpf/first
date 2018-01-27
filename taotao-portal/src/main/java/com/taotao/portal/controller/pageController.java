package com.taotao.portal.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.manager.pojo.User;
@Controller
@RequestMapping("/page")
public class pageController {
	
	
	@RequestMapping("/{page}")
	public String page(@PathVariable String page,String url,Model model){
		model.addAttribute("url",url);
		return page;
		
		
	}
}

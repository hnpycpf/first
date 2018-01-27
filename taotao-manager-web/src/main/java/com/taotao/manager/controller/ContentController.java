package com.taotao.manager.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.pojo.Content;
import com.taotao.manager.service.ContentService;

import taotao.common.pojo.TaoResult;

@Controller
@RequestMapping("/content")
public class ContentController {
	@Autowired
	private ContentService contentService;
	@RequestMapping(method=RequestMethod.POST)
	@ResponseBody
	public String add(Content content){
		contentService.saveSelective(content);
		return "success";
	}	
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public TaoResult<Content> contentByPage(Integer page,Integer rows){
		TaoResult<Content> result =contentService.queryContentByPage( page, rows);
		return result;
	}
	@ResponseBody
	@RequestMapping("/delete")
	public String delete(String[] ids){
		for (String string : ids) {
			contentService.deleteById(Long.parseLong(string));			
		}
		return "success";
	}
	@ResponseBody
	@RequestMapping(value="/edit",produces=MediaType.TEXT_HTML_VALUE)
	public String update(Content content){
		contentService.updateByIdSelective(content);
		return "success";
	}
	
	

}

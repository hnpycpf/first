package com.taotao.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.taotao.manager.pojo.ItemCat;
import com.taotao.manager.service.BaseService;
import com.taotao.manager.service.ContentService;
import com.taotao.manager.service.ItemCatService;

/*@RequestMapping()*/
@Controller
@RequestMapping("/index")
public class PageList {
	@Value("${TAOTAO_PIC_ID}")
	private String categoryId;
	@Autowired
	private ItemCatService itemCatService;
	
	@Autowired
	private ContentService contentService;
	@RequestMapping("{path}")
	public String pageList(@PathVariable("path")String path) throws JsonProcessingException{
		
		return path;
	}
	@ResponseBody
	@RequestMapping("/itemCat")
	public List<ItemCat> itemCatList(){
		List<ItemCat> list = itemCatService.queryByPage(1, 5);
		return list;
	}
	
}

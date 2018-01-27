package com.taotao.manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.pojo.ContentCategory;
import com.taotao.manager.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public List<ContentCategory> categorys(@RequestParam(value="id",defaultValue="0")Long id ){
		
		ContentCategory category=new ContentCategory();
		category.setParentId(id);
		List<ContentCategory> list = contentCategoryService.queryListByWhere(category);
		return list;
		
		
	}
	@ResponseBody
	@RequestMapping("/add")
	public ContentCategory add(ContentCategory category){
		ContentCategory category2 = contentCategoryService.add(category);
		return category2;
		
	}
	@RequestMapping("/update")
	@ResponseBody
	public String update(ContentCategory category){
		contentCategoryService.updateByIdSelective(category);
		return "success";
		
	}
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(ContentCategory category){
	
		contentCategoryService.delete(category);
		return "success";
		
	}
	

}

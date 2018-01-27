package com.taotao.manager.controller;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemCat;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemCatService;
import com.taotao.manager.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	@Autowired
	private ItemService itemService;
	@ResponseBody
	@RequestMapping("/cat")
	public List<ItemCat> itemCatList(@RequestParam(value="id",defaultValue="0") Long id){
		System.out.println(id);
		
		List<ItemCat> list = itemCatService.queryItemCatByParentId(id);
		System.out.println(list);
		return list;		
	}
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public String saveItem(Item item,String desc){
		itemService.saveItem(item, desc);
		return "aaa";
		
	}

}

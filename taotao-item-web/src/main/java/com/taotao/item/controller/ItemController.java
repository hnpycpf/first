package com.taotao.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;
	@RequestMapping("/{id}")
	public String queryItemById(@PathVariable Long id,Model model){
		Item item = itemService.queryById(id);
		ItemDesc itemDesc = itemDescService.queryById(id);
		model.addAttribute("item",item);
		model.addAttribute("itemDesc",itemDesc);
		return "item";
		
	}
}

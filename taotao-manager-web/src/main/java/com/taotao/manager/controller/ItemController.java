package com.taotao.manager.controller;

import javax.swing.text.html.FormSubmitEvent.MethodType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.pojo.Item;
import com.taotao.manager.service.ItemService;

import taotao.common.pojo.TaoResult;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService itemService;
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET)
	public TaoResult<Item> pageQueryByItems(@RequestParam(value="page",defaultValue="1")Integer page,@RequestParam(value="rows",defaultValue="30")Integer rows) {
		
		TaoResult<Item> taoResult = itemService.pageQueryByItems(page, rows);
		return taoResult;
	}
}

package com.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.manager.pojo.Item;
import com.taotao.manager.service.ItemService;
@Controller
@RequestMapping("item/interface")
public class ItemInterfaceController {
	@Autowired
	private ItemService itemService;
	@RequestMapping(value="{id}",method=RequestMethod.GET)
	public ResponseEntity<Item> getItem(@PathVariable("id")Long id){
		Item item = itemService.queryById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(item) ;	
	}
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(Item item){
		itemService.save(item);
		return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Void> update(Item item){
		itemService.updateByIdSelective(item);
		return ResponseEntity.noContent().build();
	}
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteItem(@PathVariable("id")Long id){
		itemService.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();	
	}
}

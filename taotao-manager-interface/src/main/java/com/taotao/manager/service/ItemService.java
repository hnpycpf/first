package com.taotao.manager.service;

import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;

import taotao.common.pojo.TaoResult;

public interface ItemService extends BaseService<Item> {

	public void saveItem(Item item,String desc);
	
	public TaoResult<Item> pageQueryByItems(Integer page,Integer rows);
}

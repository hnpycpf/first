package com.taotao.manager.service;

import java.util.List;

import com.taotao.manager.pojo.ItemCat;


public interface ItemCatService extends BaseService<ItemCat> {

	public List<ItemCat> queryItemCatByParentId(Long parentId);
}

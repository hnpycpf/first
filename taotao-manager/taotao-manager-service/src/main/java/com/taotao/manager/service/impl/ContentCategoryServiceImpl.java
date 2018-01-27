package com.taotao.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.taotao.manager.pojo.Content;
import com.taotao.manager.pojo.ContentCategory;
import com.taotao.manager.service.ContentCategoryService;
@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService{

	@Override
	public void delete(ContentCategory category) {
		Long parentId = category.getParentId();
		ContentCategory category1=new ContentCategory();
		category1.setParentId(parentId);
		int count = super.queryCountByWhere(category1);
		List list=new ArrayList();
		Long id = category.getId();
		list.add(id);
		search(id, list);
		if(count==0){
			ContentCategory category2=new ContentCategory();
			category2.setId(parentId);
			category2.setIsParent(false);
			super.updateByIdSelective(category2);
		}
		super.deleteByIds(list);	
	}
	public void search(Long parentId,List list){
		ContentCategory cateogry=new ContentCategory();
		cateogry.setParentId(parentId);
		List<ContentCategory> list2 = super.queryListByWhere(cateogry);
		if(list2.size()!=0){
			for (ContentCategory contentCategory : list2) {
				list.add(contentCategory.getId());
				Long id = contentCategory.getId();
				search(id,list);
			}
		}
	}
	@Override
	public ContentCategory add(ContentCategory category) {
		category.setIsParent(false);
		category.setStatus(1);
		super.saveSelective(category);
		Long parentId = category.getParentId();
		ContentCategory queryById = super.queryById(parentId);
		Boolean isParent = queryById.getIsParent();
		if(isParent!=true){
			queryById.setIsParent(true);
			super.updateByIdSelective(queryById);
		}
		return category;
	}

}

package com.taotao.manager.service;

import com.taotao.manager.pojo.ContentCategory;

public interface ContentCategoryService extends BaseService<ContentCategory> {

	public void delete(ContentCategory category);
	public ContentCategory add(ContentCategory category);

}

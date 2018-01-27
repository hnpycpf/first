package com.taotao.manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.taotao.manager.pojo.Content;

import taotao.common.pojo.TaoResult;

public interface ContentService extends BaseService<Content> {

	TaoResult<Content> queryContentByPage(Integer page, Integer rows);

	String queryBigPic(String categoryId) throws JsonProcessingException;

}

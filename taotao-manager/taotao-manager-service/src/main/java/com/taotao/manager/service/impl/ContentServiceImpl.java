package com.taotao.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.taotao.manager.pojo.Content;
import com.taotao.manager.service.ContentService;
import com.taotao.mananger.service.Redis.RedisUtils;

import taotao.common.pojo.TaoResult;
@Service
public class ContentServiceImpl extends BaseServiceImpl<Content> implements ContentService {
	@Autowired
	private RedisUtils redisUtils;
	private ObjectMapper object=new ObjectMapper();
	@Override
	public TaoResult<Content> queryContentByPage(Integer page, Integer rows) {
		
		
		List<Content> queryByPage = super.queryByPage(page, rows);
		PageInfo info=new PageInfo(queryByPage);
		return new TaoResult<Content>(info.getTotal(), queryByPage);
	}

	@Override
	public String queryBigPic(String categoryId) throws JsonProcessingException {
		
		String contentRedis = redisUtils.get("content");
		if(contentRedis!=null){
			System.out.println("从Redis数据库拿的");
			return contentRedis;
		}
		Content content=new Content();
		content.setCategoryId(Long.parseLong(categoryId));
		List<Content> list = super.queryListByWhere(content);
		List<Map<String,Object>> contentList=null;
		if(list!=null&&list.size()>0){
			 contentList=new ArrayList<>();
			for (Content searchContent : list) {
				Map<String,Object> map=new HashMap<>();			     
				map.put("srcB",null);
				map.put("height",240);
				map.put("alt",null);
				map.put("width",670);
				map.put("src",searchContent.getPic());
				map.put("widthB",550);
				map.put("href",null);
				map.put("heightB",240);
				contentList.add(map);
			}
		}
		String valueAsString = object.writeValueAsString(contentList);
		System.out.println("从数据库拿的");
		redisUtils.set("content", valueAsString, 60*60*24);
		return valueAsString ;
	}

}

package com.taotao.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.taotao.manager.pojo.Item;
import com.taotao.manager.pojo.ItemDesc;
import com.taotao.manager.service.ItemDescService;
import com.taotao.manager.service.ItemService;

import taotao.common.pojo.TaoResult;
@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
	@Autowired
	private ItemDescService itemDescService;
	@Autowired
	private Destination destination;
	private ObjectMapper object=new ObjectMapper();
	@Autowired
	private JmsTemplate jmsTemplate;
	public void saveItem(final Item item,String desc){
		super.saveSelective(item);
		ItemDesc desc1=new ItemDesc();
		desc1.setItemId(item.getId());
		desc1.setItemDesc(desc);
		itemDescService.saveSelective(desc1);
		jmsTemplate.send(destination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage text=new ActiveMQTextMessage();
				Map map=new HashMap<>();
				map.put("type", "save");
				map.put("itemId", item.getId());
				String string;
				try {
					string = object.writeValueAsString(map);
					text.setText(string);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return text;
			}
		});
	}
	@Override
	public TaoResult<Item> pageQueryByItems(Integer page, Integer rows) {
		// TODO Auto-generated method stub
		List<Item> queryByPage = super.queryByPage(page, rows);
		PageInfo info=new PageInfo<>(queryByPage);
		long total = info.getTotal();
		TaoResult<Item> result=new TaoResult<>(total,queryByPage);
		return result;
	};
	
}

package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.taotao.manager.mapper.OrderItemMapper;
import com.taotao.manager.mapper.OrderShippingMapper;
import com.taotao.manager.pojo.Order;
import com.taotao.manager.pojo.OrderItem;
import com.taotao.manager.pojo.OrderShipping;
import com.taotao.manager.pojo.User;
import com.taotao.mananger.service.Redis.RedisUtils;
import com.taotao.order.service.BaseService;
import com.taotao.order.service.OrderService;
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService   {
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	private ObjectMapper object=new ObjectMapper();
	@Value("${ORDER_ID}")
	private String ORDER_ID;
	@Value("{INCR_ORDER_ID}")
	private String INCR_ORDER_ID;
	@Override
	public String submitOrder(Order order,User user) throws Exception {
		long incr = redisUtils.incr(INCR_ORDER_ID);
		String orderTicket=""+user.getId()+incr;
		order.setOrderId(orderTicket);
		order.setUserId(user.getId());
		order.setStatus(1);
		super.saveSelective(order);
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(orderTicket);
			orderItemMapper.insertSelective(orderItem);
		}
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderTicket);
		orderShippingMapper.insertSelective(orderShipping);
		String string = object.writeValueAsString(order);
		return orderTicket;
		
		
		
		
	}
	

}

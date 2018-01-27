package com.taotao.order.service;

import com.taotao.manager.pojo.Order;
import com.taotao.manager.pojo.User;

public interface OrderService extends BaseService<Order> {

	String submitOrder(Order order, User user) throws Exception;

}

package com.taotao.portal.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.cart.service.CartService;
import com.taotao.manager.pojo.Cart;
import com.taotao.manager.pojo.Order;
import com.taotao.manager.pojo.User;
import com.taotao.order.service.OrderService;
import com.taotao.portal.cookie.CookieService;
import com.taotao.sso.service.UserService;

import utils.CookieUtils;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;
	@Autowired
	private CookieService cookieService;
	@Value("${COOKIE_NAME}")
	private String cookieName;
	@Value("${CART_COOKIE}")
	private String CART_COOKIE;
	@Autowired
	private OrderService orderService;
	@RequestMapping("/create")
	public String toOrder(HttpServletRequest request,Model model) throws Exception{
		String value = CookieUtils.getCookieValue(request, cookieName, "utf-8");
		List<Cart> list =null;
		if(value!=null){
			User user = userService.getUser(value);
			
			if(user!=null){
				 list = cartService.queryCart(user);
				
			}
		}
		 list = cookieService.queryCartByCookie(request, CART_COOKIE);
		 model.addAttribute("carts",list);
		return "order-cart";
		
	}
	/*service/order/submit*/
	@RequestMapping("/submit")
	@ResponseBody
	public Map<String,Object> submitOrder(HttpServletRequest request,Order order) throws Exception{
		
		User user = (User) request.getAttribute("user");
		String string = orderService.submitOrder(order,user);
		Map<String,Object> map=new HashMap<>();
		map.put("status", "200");
		map.put("data",string);
		return map;
		
	}
	@RequestMapping("/success")
	public String success(String id,Model model){
		Order order = orderService.queryById(id);
		model.addAttribute("order",order);
		model.addAttribute("date", new DateTime().plusDays(2).toString("yyyy年MM月dd日HH时mm分ss秒"));
		return "success";
	}
}

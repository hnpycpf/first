package com.taotao.portal.cart.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.taotao.cart.service.CartService;
import com.taotao.manager.pojo.Cart;
import com.taotao.manager.pojo.User;
import com.taotao.portal.cookie.CookieService;
import com.taotao.sso.service.UserService;

import utils.CookieUtils;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	@Value("${COOKIE_NAME}")
	private String cookieName;
	@Autowired
	private UserService userService;
	@Value("${CART_COOKIE}")
	private String CART_COOKIE;
	@Autowired
	private CookieService cookieService;
	@RequestMapping("{itemId}")
	public String saveCart(@PathVariable Long itemId,HttpServletRequest request,Long num,HttpServletResponse response) throws Exception{
		String value = CookieUtils.getCookieValue(request, cookieName, "utf-8");
		if(value!=null){
			User user = userService.getUser(value);
			if(user!=null){
				cartService.saveItemByCart(user,itemId,num);
				
			}
		}
		cookieService.saveCartByCookie(request,response,CART_COOKIE,itemId,num);
		return "redirect:/cart/show.html";
		
	}
	@RequestMapping("/show")
	public String queryCart(HttpServletRequest request,Model model) throws Exception{
		String value = CookieUtils.getCookieValue(request, cookieName, "utf-8");
		List<Cart> list =null;
		if(value!=null){
			User user = userService.getUser(value);
			
			if(user!=null){
				 list = cartService.queryCart(user);
				
			}
		}
		 list = cookieService.queryCartByCookie(request, CART_COOKIE);
		 model.addAttribute("cartList",list);
		return "cart";
	}
	@RequestMapping("update/num/{itemId}/{num}")
	@ResponseBody
	public void updateCart(HttpServletRequest request,@PathVariable String itemId,@PathVariable Long num,HttpServletResponse response) throws Exception{
		String value = CookieUtils.getCookieValue(request, cookieName, "utf-8");
		if(value!=null){
			User user = userService.getUser(value);
			if(user!=null){
			cartService.updateCart(user,itemId,num);
				
			}
		}
		cookieService.updateCartByCookie(request,response,CART_COOKIE,itemId,num);
		
	}
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String value = CookieUtils.getCookieValue(request, cookieName, "utf-8");
		if(value!=null){
			User user = userService.getUser(value);
			if(user!=null){
			cartService.deleteCart(user,itemId);
				
			}
		}
		else{
			cookieService.deleteCartByCookie(request,response,CART_COOKIE,itemId);
		}
		return "redirect:/cart/show.html";
	}
}

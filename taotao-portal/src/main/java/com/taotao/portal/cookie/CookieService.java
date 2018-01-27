package com.taotao.portal.cookie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manager.pojo.Cart;
import com.taotao.manager.pojo.Item;
import com.taotao.manager.service.ItemService;

import utils.CookieUtils;

@Controller
public class CookieService {
	private ObjectMapper object=new ObjectMapper();
	@Autowired
	private ItemService itemService;
	public void saveCartByCookie(HttpServletRequest request, HttpServletResponse response, String cART_COOKIE,
			Long itemId, Long num) {
		try {
			List<Cart> list = queryCartByCookie(request,cART_COOKIE);
			Cart isCart=null;
			for (Cart cart : list) {
				if(cart.getItemId().intValue()==itemId.intValue()){
					isCart=cart;
				}
			}
			if(isCart!=null){
				isCart.setNum((isCart.getNum()+num.intValue()));
			}
			else{
				Item item = itemService.queryById(itemId);
				Cart cart=new Cart();
				cart.setItemId(item.getId());
				if(item.getImages()!=null){
					cart.setItemImage(item.getImages()[0]);
				}
				cart.setItemPrice(item.getPrice());
				cart.setItemTitle(item.getTitle());
				cart.setNum(num.intValue());
				cart.setUpdated(new Date());
				list.add(cart);
			}
			String valueAsString = object.writeValueAsString(list);
			CookieUtils.setCookie(request, response, cART_COOKIE, valueAsString,60*60*60,true);
		} catch (Exception e) {
		
	}
	}
	public List<Cart> queryCartByCookie(HttpServletRequest request,String cART_COOKIE) throws JsonParseException, JsonMappingException, IOException{
		String cookieValue = CookieUtils.getCookieValue(request, cART_COOKIE,true);
		List<Cart> readValue=null;
		if(cookieValue!=null){
			 readValue = object.readValue(cookieValue, object.getTypeFactory().constructCollectionType(List.class, Cart.class));
			 return readValue;
		}
		else{
			return new ArrayList<Cart>();
		}	
	}
	public void updateCartByCookie(HttpServletRequest request, HttpServletResponse response, String cART_COOKIE,
			String itemId, Long num) {
		try {
			List<Cart> list = queryCartByCookie(request, cART_COOKIE);
			for (Cart cart : list) {
				if(cart.getItemId().intValue()==Long.parseLong(itemId)){
					cart.setNum(num.intValue());
					cart.setCreated(new Date());
					String string = object.writeValueAsString(list);
					CookieUtils.setCookie(request, response, cART_COOKIE, string,60*60*60,true);
					break;
				}
			}
		} catch (Exception e) {
		
		
	}
	
	
	}
	public void deleteCartByCookie(HttpServletRequest request, HttpServletResponse response, String cART_COOKIE,
			Long itemId) {
		try {
			List<Cart> list = queryCartByCookie(request, cART_COOKIE);
			for (Cart cart : list) {
				if(cart.getItemId().intValue()==itemId.intValue()){
					list.remove(cart);
					String string = object.writeValueAsString(list);
					CookieUtils.setCookie(request, response, cART_COOKIE, string,60*60*60,true);
					break;
				}
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

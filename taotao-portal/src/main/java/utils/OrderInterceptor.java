package utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.cart.service.CartService;
import com.taotao.manager.pojo.Cart;
import com.taotao.manager.pojo.User;
import com.taotao.sso.service.UserService;

public class OrderInterceptor implements HandlerInterceptor {
	@Autowired
	private CartService cartService;
	@Value("${COOKIE_NAME}")
	private String cookieName;
	@Autowired
	private UserService userService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		StringBuffer requestURL = request.getRequestURL();
		String value = CookieUtils.getCookieValue(request, cookieName, "utf-8");
		List<Cart> list =null;
		if(value!=null){
			User user = userService.getUser(value);
			request.setAttribute("user", user);
			if(user!=null){
				 return true;
				
			}
		}
		response.sendRedirect("http://www.taotao.com/page/login.html?url="+requestURL);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}

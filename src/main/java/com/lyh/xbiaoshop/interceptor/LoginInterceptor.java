package com.lyh.xbiaoshop.interceptor;

import com.lyh.xbiaoshop.entity.User;
import com.lyh.xbiaoshop.utils.LoginUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class LoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private RedisTemplate redisTemplate;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Long userId = LoginUserUtils.getLoginUserId();
		String contextPath = request.getContextPath();
		String requestURI1 = request.getRequestURI();
		if ( userId==null ){
			response.sendRedirect(contextPath+"/login.html");
			return false;
		}
		User loginUser =
				(User)redisTemplate.opsForValue().get("loginuser:" + userId);
		String requestURI = request.getRequestURI();
		if (loginUser==null) {
			Cookie cookie = new Cookie("cookieuser", "");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			response.sendRedirect(contextPath+"/login.html");
			return false;
		}
		return true;
	}
}

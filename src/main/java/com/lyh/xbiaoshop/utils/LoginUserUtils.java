package com.lyh.xbiaoshop.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Component
public class LoginUserUtils {
	
	
	private static HttpServletRequest request;
	
	
	@Autowired
	public void setRequest(HttpServletRequest request) {
		LoginUserUtils.request = request;
	}
	
	public static Long getLoginUserId() {
		Cookie[] cookies = request.getCookies();
		Long userid = null;
		if ( cookies != null ) {
			for ( Cookie cookie : cookies ) {
				if ( "cookieuser".equals(cookie.getName()) ) {
					try {
						ObjectMapper objectMapper = new ObjectMapper();
						String value = cookie.getValue();
						 userid = userid = objectMapper.readValue(URLDecoder.decode(value, "utf8"), Long.class);
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return userid;
	}
}

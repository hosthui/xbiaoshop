package com.lyh.xbiaoshop.config;


import com.lyh.xbiaoshop.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class springmvcConfig implements WebMvcConfigurer {
	@Autowired
	LoginInterceptor loginInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns(new String[]{"/xbiaoshop/**","/*.html"})
				.excludePathPatterns(new String[]{"/login.html","/changePwd.html",
						"/register.html","/wx_login_info.html"});
	
	}
}

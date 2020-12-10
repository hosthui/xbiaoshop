package com.lyh.xbiaoshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyh.xbiaoshop.entity.Result;
import com.lyh.xbiaoshop.entity.User;
import com.lyh.xbiaoshop.service.UserService;
import com.lyh.xbiaoshop.service.impl.UserServiceImpl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class WxLoginController {
	@Value("${wx.appId}")
	private String appId;
	
	@Value("${wx.redirect_uri}")
	private String redirectUri;
	
	@Value("${wx.appSecret}")
	private String appSecret;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	HttpServletResponse response;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@RequestMapping("to_wxLogin")
	public void toWxLogin() throws IOException {
		String url="https://open.weixin.qq.com/connect/qrconnect?" +
				"appid="+appId +
				"&redirect_uri="+redirectUri +
				"&response_type=code" +
				"&scope=snsapi_login";
		
		response.sendRedirect(url); //发送请求返回二维码
	}
	
	@RequestMapping("wx_login")
	public void wxLogin() throws IOException {
		String code = request.getParameter("code");     //获取请求中的code
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?" +
				"appid="+ appId+
				"&secret="+appSecret +
				"&code="+code +
				"&grant_type=authorization_code";       //通过请求的code
		// 发送请求获取access_token
		HashMap execute = execute(url);     //获取access_token
		url="https://api.weixin.qq.com/sns/userinfo?" +
				"access_token="+ execute.get("access_token") +
				"&openid="+execute.get("openid");
		HashMap usermes = execute(url); //获取用户信息
		User user = new User();
		user.setWxOpenid((String)usermes.get("openid"));
		User user1 = userService.findOne(user);
		if ( user1==null ){
			//第一次扫码，保存用户信息
			user = new User();
			
			// 用户的头像
			user.setPic(usermes.get("headimgurl").toString());
			
			// 性别
			user.setSex(usermes.get("sex").toString());
			// 用户的昵称
			user.setRealName(usermes.get("nickname").toString());
			
			// 随机用户名(11位随机字符串)
			user.setUserName(UUID.randomUUID().toString().substring(36 - 15));
			
			user.setNativePlace(usermes.get("country").toString());
			user.setWxOpenid(execute.get("openid").toString());
			
			// 密码默认admin
			user.setPassword("admin");
			// 注册一个新的用户,并回调user
			user.setRegisterTime(new Date());
			user1 = userService.add(user);
			
		}
		//存在用户信息直接登录
		//更新用户登录时间
		userService.update(user1);
		redisTemplate.opsForValue().set("loginuser:" + user1.getUserId(), user1, 7,
				TimeUnit.DAYS);
		redisTemplate.opsForValue().set("userId", user1.getUserId(), 60,
				TimeUnit.SECONDS);
		response.sendRedirect("wx_login_info.html");
	}
	
	
	/**
	 *  获取用户信息
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/getWxLoginInfo")
	public Result getWxLoginInfo() throws IOException {
		Object userId = redisTemplate.opsForValue().get("userId");
		ObjectMapper objectMapper = new ObjectMapper();
		Map returnMap = new HashMap<>();
		
		returnMap.put("loginUser", redisTemplate.opsForValue().get("loginuser:" + userId));
		returnMap.put("userId", userId);
		String s = objectMapper.writeValueAsString(userId);
		Cookie cookie = new Cookie("cookieuser", URLEncoder.encode(s, "utf-8"));
		cookie.setPath("/");
		cookie.setMaxAge(7*24*60*60);
		response.addCookie(cookie);
		return new Result(true, "获取微信登录信息成功", returnMap);
	}
	
	public HashMap execute(String url){
		CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(url);
		
		try {
			HttpResponse response = closeableHttpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();//获取响应信息
			if ( entity!=null ){
				String json = EntityUtils.toString(entity, Charset.forName(
						"utf8"));
				return new ObjectMapper().readValue(json, HashMap.class);
				//通过jacson转map
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}

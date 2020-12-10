package com.lyh.xbiaoshop.controller;


import com.baomidou.kaptcha.Kaptcha;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyh.xbiaoshop.entity.Result;
import com.lyh.xbiaoshop.entity.User;
import com.lyh.xbiaoshop.service.UserService;
import com.lyh.xbiaoshop.service.impl.UserServiceImpl;
import com.lyh.xbiaoshop.utils.LoginUserUtils;
import com.lyh.xbiaoshop.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private Kaptcha kaptcha;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/loginIn")
	public Result loginin(@RequestBody Map<String,String> logmsg, HttpServletResponse response){
		if ( "".equals(logmsg.get("username"))||"".equals(logmsg.get("password")) ){
			return new Result(false,"用户名或密码不能为空");
		}
		if ( "".equals(logmsg.get("code")) ){
			return new Result(false,"请填写验证码");
		}
		if (kaptcha.validate(logmsg.get("code"))){
			User user = new User();
			user.setUserName(logmsg.get("username"));
			user.setPassword(logmsg.get("password"));
			User loginUser = userService.findOne(user);
			if ( loginUser!=null ){
				loginUser.setPassword(null);
				ObjectMapper objectMapper = new ObjectMapper();
				Cookie cookie=null;
				try {
					String s = objectMapper.writeValueAsString(loginUser.getUserId());
					cookie=new Cookie("cookieuser",
							URLEncoder.encode(s,"utf-8"));
				} catch (JsonProcessingException | UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				cookie.setPath("/");
				if ( !"".equals(logmsg.get("rem"))&&"true".equals(logmsg.get(
						"rem")) ){
					cookie.setMaxAge(7*24*60*60);
					redisTemplate.opsForValue().set("loginuser:"+loginUser.getUserId(),loginUser,7, TimeUnit.DAYS);
				}else {
					redisTemplate.opsForValue().set("loginuser:"+loginUser.getUserId(),loginUser,30, TimeUnit.MINUTES);
				}
				response.addCookie(cookie);
				return new Result(true,"登录成功",loginUser);
			}
		}
		return new Result(false,"登录失败,用户名或密码错误");
	}
	@RequestMapping("/checkemail")
	public Result checkEmail(User user){
		User one = userService.findOne(user);
		if ( one!=null ){
			return new Result(true,"邮箱已经注册");
		}
		return new Result(false,"邮箱未注册");
		
	}
	@RequestMapping("/checkusername")
	public Result checkUsername(User user){
		User one =userService.findOne(user);
		if ( one!=null ){
			return new Result(true,"用户名已经注册");
		}
		return new Result(false,"用户名未注册");
		
	}
	@RequestMapping("/register")
	public Result Register(@RequestBody User user){
		user.setRegisterTime(new Date());
		userService.add(user);
		return new Result(true,"注册成功");
	}
	
	@RequestMapping("/send")
	public Result  sendEmail(@RequestParam String email,
	                         @RequestParam String username){
		if ( StringUtils.isEmpty(email) ){
			return new Result(false,"邮箱不能为空");
		}
		if ( StringUtils.isEmpty(username) ){
			return new Result(false,"用户名不能为空");
		}
		User user = new User();
		user.setEmail(email);
		user.setUserName(username);
		User one = userService.findOne(user);
		if ( one!=null ){
			String code = MailUtils.verifyCode();
//			MailUtils.sendMail(email,code);
			MailUtils.sendMail("yun89688@gmail.com",code);//定死
			redisTemplate.opsForValue().set("verifyCode",code,60,TimeUnit.SECONDS);
			return new Result(true,"验证吗已发送");
		}
		return  new Result(false,"未找到该用户");
	}
	@RequestMapping(value = "/reset/{code}",method = RequestMethod.PUT)
	public Result resetPws(@PathVariable String code, @RequestBody User user){
		if ( code==null|| "".equals(code)){
			return new Result(false,"验证码不能为空");
		}
		String verifyCode = (String)redisTemplate.opsForValue().get("verifyCode");
		if ( verifyCode==null|| "".equals(verifyCode)){
			return new Result(false,"验证码过期");
		}
		if ( verifyCode.equals(code) ){
			userService.updateUE(user);
			redisTemplate.delete("verifyCode");
			return new Result(true,"修改成功");
		}else {
			redisTemplate.delete("verifyCode");
			return new Result(false,"验证码错误，请重新发送");
		}
	}
	@RequestMapping("/loginout")
	public Result loginout(HttpServletResponse response){
		Long loginUserId = LoginUserUtils.getLoginUserId();
		redisTemplate.delete("loginuser:"+loginUserId);
		Cookie cookie1 = new Cookie("cookieuser", null);
		cookie1.setMaxAge(0);   //秒
		cookie1.setPath("/");
		response.addCookie(cookie1);
		return new Result(true,"注销成功");
	}

}

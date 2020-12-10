package com.lyh.xbiaoshop.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/main")
public class MainController {
	@RequestMapping("toHome")
	public ModelAndView home(){
		return new ModelAndView("/home.html");
	}
	@RequestMapping("header")
	public ModelAndView sidebar(){
		return new ModelAndView("/header.html");
	}
}

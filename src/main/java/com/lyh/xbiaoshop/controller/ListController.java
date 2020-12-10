package com.lyh.xbiaoshop.controller;


import com.lyh.xbiaoshop.entity.Result;
import com.lyh.xbiaoshop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/list")
@RestController
public class ListController {
	
	@Autowired
	private GoodsService goodsService;
	
	
	@RequestMapping("/findlist")
	public Result findList() {
		return new Result(true, "", goodsService.listAll());
	}
	
	@RequestMapping("/hot")
	public ModelAndView findHot() {
		return new ModelAndView("/HotList.html");
	}
	
	@RequestMapping("/affordable")
	public ModelAndView findAff() {
		return new ModelAndView("/AffordableList.html");
	}
	
	@RequestMapping("/electronics")
	public ModelAndView findEle() {
		return new ModelAndView("/ElectronicsList.html");
	}
	
	@RequestMapping("/fashion")
	public ModelAndView findFashion() {
		return new ModelAndView("/fashionList.html");
	}
	
	@RequestMapping("/book")
	public ModelAndView findBook() {
		return new ModelAndView("/bookList.html");
	}
}

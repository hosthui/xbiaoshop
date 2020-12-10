package com.lyh.xbiaoshop.controller;


import com.lyh.xbiaoshop.entity.Result;
import com.lyh.xbiaoshop.service.GoodsService;
import com.lyh.xbiaoshop.service.impl.GoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {
	
	
	@Autowired
	private GoodsService goodsService;
	
	
	@RequestMapping("/selectnew")
	public Result selectNew(){
		return new Result(true,"查询成功",goodsService.newGoods());
	}
	
	@RequestMapping("/selectbargain")
	public Result selectBar(){
	return new Result(true,"查询成功",goodsService.barGoods());
	}
	

}

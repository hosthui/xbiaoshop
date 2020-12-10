package com.lyh.xbiaoshop.controller;

import com.github.pagehelper.PageInfo;
import com.lyh.xbiaoshop.entity.Car;
import com.lyh.xbiaoshop.entity.Goods;
import com.lyh.xbiaoshop.entity.Result;
import com.lyh.xbiaoshop.service.GoodsService;
import com.lyh.xbiaoshop.service.impl.GoodsServiceImpl;
import com.lyh.xbiaoshop.utils.LoginUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	
	@RequestMapping("/detail")
	public ModelAndView toMyUser() {
		return new ModelAndView("/goodsDetail.html");
	}
	
	@RequestMapping("/selectbargain")
	public Result selectBar(@RequestParam Integer typeid) {
		return new Result(true, "查询成功", goodsService.barGoodsByType(typeid));
	}
	
	@RequestMapping(value = "uplook/{gid}", method = RequestMethod.PUT)
	public Result upLook(@PathVariable Long gid) {
		Long loginUserId = LoginUserUtils.getLoginUserId();
		return new Result(true, "更新成功", goodsService.addLook(loginUserId, gid));
	}
	
	//collect
	@RequestMapping(value = "/collect/{gid}", method = RequestMethod.POST)
	public Result Collect(@PathVariable Long gid) {
		Long loginUserId = LoginUserUtils.getLoginUserId();
		boolean b = goodsService.addCollect(loginUserId, gid);
		if ( b ) {
			return new Result(true, "收藏成功");
		} else {
			return new Result(true, "取消成功");
		}
	}
	
	@RequestMapping(value = "/addcar", method = RequestMethod.POST)
	public Result addCar(@RequestBody Car car) {
		Long loginUserId = LoginUserUtils.getLoginUserId();
		car.setuId(loginUserId);
		goodsService.addCar(car);
		return new Result(true, "添加成功");
	}
	
	
	@RequestMapping("/findhot/{page}")
	public Result findHot(@PathVariable Integer page) {
		PageInfo<Goods> hotList = goodsService.findHotList(page);
		return new Result(true, "查询成功", hotList);
	}
	
	@RequestMapping("/findaffordable/{page}")
	public Result findAffordable(@PathVariable Integer page) {
		PageInfo<Goods> hotList = goodsService.findAffordableList(page);
		return new Result(true, "查询成功", hotList);
	}
	
	@RequestMapping("/findelectronics/{page}")
	public Result findElectronics(@PathVariable Integer page) {
		PageInfo<Goods> hotList = goodsService.findByType(page,1);
		return new Result(true, "查询成功", hotList);
	}
	
	@RequestMapping("/findfashion/{page}")
	public Result findFashion(@PathVariable Integer page) {
		PageInfo<Goods> hotList = goodsService.findByType(page,2);
		return new Result(true, "查询成功", hotList);
	}
	@RequestMapping("/findbook/{page}")
	public Result findBook(@PathVariable Integer page) {
		PageInfo<Goods> hotList = goodsService.findByType(page,3);
		return new Result(true, "查询成功", hotList);
	}
	@RequestMapping("/findsearch/{page}")
	public Result findSearch(@PathVariable Integer page,
	                         @RequestParam String message) {
		PageInfo<Goods> hotList = goodsService.findSearchList(page,message);
		return new Result(true, "查询成功", hotList);
	}
}

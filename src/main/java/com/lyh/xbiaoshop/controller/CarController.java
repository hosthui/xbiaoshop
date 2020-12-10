package com.lyh.xbiaoshop.controller;
import com.lyh.xbiaoshop.entity.Car;
import com.lyh.xbiaoshop.entity.Goods;
import com.lyh.xbiaoshop.entity.Result;
import com.lyh.xbiaoshop.service.GoodsService;
import com.lyh.xbiaoshop.service.UserService;
import com.lyh.xbiaoshop.utils.LoginUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/car")
@RestController
public class CarController {
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/findcars")
	public Result findCars(){
		Long userId = LoginUserUtils.getLoginUserId();
		return new Result(true,"查询成功",goodsService.findCars(userId)) ;
	}

	
	@RequestMapping(value = "/delcar/{gId}",method = RequestMethod.PUT)
	public Result delCar(@PathVariable Long gId){
		Long userId = LoginUserUtils.getLoginUserId();
		Car car = new Car();
		car.setuId(userId);
		car.setgId(gId);
		userService.delCar(car);
		return new Result(true,"删除成功");
	}
	@RequestMapping(value = "/settle/{gId}/{gNum}",method = RequestMethod.POST)
	public Result settle(@PathVariable Long gId,@PathVariable Integer gNum){
		Long userId = LoginUserUtils.getLoginUserId();
		Car car = new Car();
		car.setuId(userId);
		car.setgId(gId);
		car.setgNum(gNum);
		userService.addOrder(car);
		car.setgNum(null);
		userService.delCar(car);
		return new Result(true,"订单添加成功");
	}
	@RequestMapping(value = "/settleAll",method = RequestMethod.POST)
	public Result settleAll(@RequestBody List<Goods> goodsList){
		Long userId = LoginUserUtils.getLoginUserId();
		userService.addOrderAll(goodsList,userId);
		userService.delCarAll(userId);
		return new Result(true,"订单添加成功");
	}
}

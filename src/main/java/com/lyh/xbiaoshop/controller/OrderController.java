package com.lyh.xbiaoshop.controller;


import com.lyh.xbiaoshop.entity.Car;
import com.lyh.xbiaoshop.entity.Order;
import com.lyh.xbiaoshop.entity.Result;
import com.lyh.xbiaoshop.service.OrderService;
import com.lyh.xbiaoshop.utils.LoginUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/order")
@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	
	@RequestMapping("/findorders")
	public Result findOrders(){
		Long userId = LoginUserUtils.getLoginUserId();
		return new Result(true,"查询成功",orderService.findOrders(userId,0)) ;
	}
	@RequestMapping(value = "/delorder/{oId}",method = RequestMethod.PUT)
	public Result delOrder(@PathVariable Long oId){
		orderService.delOrder(oId);
		return new Result(true,"删除成功");
	}
	@RequestMapping(value = "/pay/{oId}",method = RequestMethod.PUT)
	public Result pay(@PathVariable Long oId){
		orderService.updateStatus(oId,1);
		return new Result(true,"付款成功");
	}
	
	@RequestMapping(value = "/payAll",method = RequestMethod.PUT)
	public Result payAll(){
		Long userId = LoginUserUtils.getLoginUserId();
		orderService.updateAllStatus(userId);
		return new Result(true,"付款成功");
	}
}

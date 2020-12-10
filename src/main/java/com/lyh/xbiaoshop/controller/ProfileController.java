package com.lyh.xbiaoshop.controller;


import com.github.pagehelper.PageInfo;
import com.lyh.xbiaoshop.entity.Address;
import com.lyh.xbiaoshop.entity.Goods;
import com.lyh.xbiaoshop.entity.Result;
import com.lyh.xbiaoshop.entity.User;
import com.lyh.xbiaoshop.service.GoodsService;
import com.lyh.xbiaoshop.service.OrderService;
import com.lyh.xbiaoshop.service.UserService;
import com.lyh.xbiaoshop.utils.FTPUtilsTwo;
import com.lyh.xbiaoshop.utils.LoginUserUtils;
import com.lyh.xbiaoshop.utils.PostCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
public class ProfileController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	 private Environment evn;
	
	@RequestMapping(value = "/uploadpic",method = RequestMethod.POST)
	public Result uploadPic(MultipartFile pic) throws IOException {
		String originalFilename = pic.getOriginalFilename();
		String sufix =
				originalFilename.substring(originalFilename.lastIndexOf("."));
		String fileName= UUID.randomUUID()+sufix;
		Long userId = LoginUserUtils.getLoginUserId();
		String url =
				evn.getProperty("xbiaoshop.uploads-url") + "user/" + fileName;
		User user = userService.upPic(userId, url);
		InetAddress localHost=null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
//		File file =
//				new File(evn.getProperty("xbiaoshop.uploads-file")+
//						"user\\"+fileName);
//		pic.transferTo(file);
		String s = evn.getProperty("ftp.username");
		FTPUtilsTwo.uploadFile(localHost.getHostAddress(),
				evn.getProperty("ftp.username"),
				evn.getProperty("ftp.password"),
				Integer.parseInt(evn.getProperty("ftp.port")),"/user",fileName,pic.getInputStream());
		return new Result(true,"上传成功",user);
	}
	
	@RequestMapping(value = "/doupdate",method = RequestMethod.PUT)
	public Result doUpdate(@RequestBody User user){
		User update = userService.update(user);
		return new Result(true,"更新成功",update);
		
	}
	
	@RequestMapping(value = "doUpPassword",method = RequestMethod.PUT)
	public Result upPassword(@RequestBody Map<String,Object> pasMes){
		Long userId = LoginUserUtils.getLoginUserId();
		pasMes.put("userId",userId);
		List<User> search = userService.findSearch(pasMes);
		if ( search.size()==1){
			userService.upPassword(pasMes);
			return new Result(true,"密码重置成功");
		}
		return new Result(false,"原密码错误");
	}
	
	@RequestMapping("findcollect/{page}")
	public Result findCollect(@PathVariable Integer page){
		Long userId = LoginUserUtils.getLoginUserId();
		PageInfo<Goods> collect = goodsService.findCollect(userId, page);
		return new Result(true,"查询成功",collect);
	}
	@RequestMapping(value = "addAddress/{isdef}",method = RequestMethod.POST)
	public Result addAddress(@RequestBody Address address,
	                         @PathVariable Integer isdef){
		Long userId = LoginUserUtils.getLoginUserId();
		address.setuId(userId);
		address.setPostcode(PostCodeUtils.getPostCodeByAddr(address.getAddress()));
		Long aLong = userService.addAddress(address);
		if ( isdef==1 ){
			User user = new User();
			user.setUserId(userId);
			user.setDefaultAddressId(aLong);
			User update = userService.update(user);
			return new Result(true,"添加成功",update);
		}
		return new Result(true,"添加成功");
	
	}
	
	@RequestMapping("findAddress")
	public Result findAddress(){
		Long userId = LoginUserUtils.getLoginUserId();
		List<Address> addresses = userService.addressAll(userId);
		return new Result(true,"查询成功",addresses);
	}
	
	@RequestMapping(value = "delAddress/{aId}",method = RequestMethod.PUT)
	public Result delAddress(@PathVariable Long aId){
		userService.delAddress(aId);
		return new Result(true,"删除成功");
	}
	@RequestMapping(value = "resetdef/{aId}",method = RequestMethod.PUT)
	public Result reSetDef(@PathVariable Long aId){
		Long userId = LoginUserUtils.getLoginUserId();
		User user = new User();
		user.setUserId(userId);
		user.setDefaultAddressId(aId);
		User update = userService.update(user);
		return new Result(true,"设置成功",update);
	}
	@RequestMapping("countcar")
	public Result countCar(){
		Long userId = LoginUserUtils.getLoginUserId();
		long count= userService.countUserCar(userId);
		return new Result(true,"查询成功",count);
	}
	@RequestMapping("countorder")
	public Result countOrder(){
		Long userId = LoginUserUtils.getLoginUserId();
		long count= userService.countUserOrder(userId);
		return new Result(true,"查询成功",count);
	}
	
	@RequestMapping("findpayOrder")
	public Result findPayOrder(){
		Long userId = LoginUserUtils.getLoginUserId();
		List<Goods> orders = orderService.findOrders(userId, 1);
		return new Result(true,"查询成功",orders);
	}
	@RequestMapping("findputorder")
	public Result findPutOrder(){
		Long userId = LoginUserUtils.getLoginUserId();
		List<Goods> orders = orderService.findOrders(userId, 2);
		return new Result(true,"查询成功",orders);
	}
	
	@RequestMapping(value = "confirmreceipt/{oId}",method = RequestMethod.PUT)
	public Result confirmReceipt(@PathVariable Long oId){
		orderService.updateStatus(oId,2);
		return new Result(true,"已确认收货");
	}
}

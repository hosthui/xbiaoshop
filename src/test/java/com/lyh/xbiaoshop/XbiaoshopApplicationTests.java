package com.lyh.xbiaoshop;

import com.lyh.xbiaoshop.constant.SysConstant;
import com.lyh.xbiaoshop.entity.Goods;
import com.lyh.xbiaoshop.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class XbiaoshopApplicationTests {
	
	@Autowired
	private GoodsService goodsService;

	@Test
	void contextLoads() {
		//http://localhost/uploads/goods/big_1.jpg
		String s="http://localhost/uploads/goods/big_1.jpg";
		String localhost = s.replace("localhost", "127.0.0.1");
		System.out.println(localhost);
	}

}

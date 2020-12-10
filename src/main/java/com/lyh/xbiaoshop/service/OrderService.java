package com.lyh.xbiaoshop.service;

import com.lyh.xbiaoshop.entity.Goods;
import com.lyh.xbiaoshop.entity.Order;

import java.util.List;

public interface OrderService extends BaseService<Order,Long>{
	List<Goods> findOrders(Long userId,Integer status);
	
	void delOrder(Long oId);
	
	void updateStatus(Long oId,Integer status);
	
	void updateAllStatus(Long userId);
}

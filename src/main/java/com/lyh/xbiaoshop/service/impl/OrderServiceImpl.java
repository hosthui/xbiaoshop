package com.lyh.xbiaoshop.service.impl;

import com.lyh.xbiaoshop.dao.OrderDao;
import com.lyh.xbiaoshop.entity.Goods;
import com.lyh.xbiaoshop.entity.Order;
import com.lyh.xbiaoshop.mapper.GoodsMapper;
import com.lyh.xbiaoshop.mapper.OrderMapper;
import com.lyh.xbiaoshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OrderServiceImpl extends BaseServiceImpl<Order,Long> implements OrderService {
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private OrderDao orderDao;
	
	
	@Override
	public List<Goods> findOrders(Long userId,Integer status) {
		List<Goods> orders = goodsMapper.selectOrders(userId,status);
		return orders;
	}
	
	@Override
	public void delOrder(Long oId) {
		orderDao.deleteById(oId);
	}
	
	@Override
	@Transactional
	public void updateStatus(Long oId,Integer status) {
		orderDao.updateOrderByOrderId(oId,status);
	}
	
	@Override
	@Transactional
	public void updateAllStatus(Long userId) {
		orderDao.updateOrderByUserIdAndStatus(userId,1);
	}
}

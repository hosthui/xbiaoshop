package com.lyh.xbiaoshop.service;


import com.github.pagehelper.PageInfo;
import com.lyh.xbiaoshop.entity.Car;
import com.lyh.xbiaoshop.entity.Goods;
import com.lyh.xbiaoshop.entity.ProductList;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface GoodsService extends BaseService<Goods,Long> {
	List<Goods> findAll();
	
	Page<Goods> findSearch(Map whereMap, int page, int size);
	
	List<Goods> findSearch(Map whereMap);
	
	Goods findById(Long id);
	
	void add(Goods goods);
	
	void update(Goods goods);
	
	void deleteById(Long id);
	
	List<Goods> newGoods();
	
	List<Goods> barGoods();
	
	List<Goods> barGoodsByType(Integer typeid);
	
	boolean addCollect(Long uid, Long gid);
	
	@Transactional
	boolean addLook(Long uid, Long gid);
	
	@Transactional
	void addCar(Car car);
	
	PageInfo<Goods> findCollect(Long uid,Integer page);
	
	List<Goods> findCars(Long uId);
	
	List<ProductList> listAll();
	
	PageInfo<Goods> findHotList(Integer page);
	
	PageInfo<Goods> findAffordableList(Integer page);
	
	PageInfo<Goods> findByType(Integer page,Integer typeId);
	
	PageInfo<Goods> findSearchList(Integer page,String message);
}

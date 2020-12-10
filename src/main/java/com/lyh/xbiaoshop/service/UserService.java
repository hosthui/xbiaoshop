package com.lyh.xbiaoshop.service;

import com.lyh.xbiaoshop.entity.Address;
import com.lyh.xbiaoshop.entity.Car;
import com.lyh.xbiaoshop.entity.Goods;
import com.lyh.xbiaoshop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface UserService extends BaseService<User,Long> {
	List<User> findAll();
	
	Page<User> findSearch(Map whereMap, int page, int size);
	
	List<User> findSearch(Map whereMap);
	
	User findById(Long id);
	
	User add(User user);
	
	User update(User user);
	
	@Transactional
	User upPic(Long userId, String picUrl);
	
	@Transactional
	void updateUE(User user);
	
	void deleteById(Long id);
	
	User findOne(User user);
	
	void upPassword(Map<String, Object> pasMes);
	
	Long addAddress(Address address);
	
	List<Address> addressAll(Long userId);
	
	void delAddress(Long aId);
	
	void delCar(Car car);
	
	void addOrder(Car car);
	
	void delCarAll(Long userId);
	
	void addOrderAll(List<Goods> goodsList,Long userId);
	
	long countUserCar(Long userId);
	
	long countUserOrder(Long userId);
}

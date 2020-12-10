package com.lyh.xbiaoshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.lyh.xbiaoshop.dao.AddressDao;
import com.lyh.xbiaoshop.dao.CarDao;
import com.lyh.xbiaoshop.dao.OrderDao;
import com.lyh.xbiaoshop.dao.UserDao;
import com.lyh.xbiaoshop.entity.*;
import com.lyh.xbiaoshop.mapper.CarMapper;
import com.lyh.xbiaoshop.mapper.OrderMapper;
import com.lyh.xbiaoshop.mapper.UserMapper;
import com.lyh.xbiaoshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Description user 服务层
 * @author admin
 * @date 2020-12-06 10:30:30
*/
@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private AddressDao addressDao;
	
	
	@Autowired
	private CarDao carDao;
	
	
	@Autowired
	private CarMapper carMapper;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private OrderMapper orderMapper;

	/**
	* 查询全部列表
	* @return
	*/
	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	/**
	* 条件查询+分页
	* @param whereMap
	* @param page
	* @param size
	* @return
	*/
	@Override
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	/**
 	* 条件查询
	* @param whereMap
	* @return
	*/
	@Override
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	* 根据ID查询实体
	* @param id
	* @return
	*/
	@Override
	public User findById(Long id) {
		return userDao.findById(id).get();
	}

	/**
	* 增加
	* @param user
	*/
	
	@Override
	public User add(User user) {
		User save = userDao.save(user);
		return save;
	}

	/**
	* 修改
	* @param user
	*/
	@Override
	public User update(User user) {
		userMapper.updateByPrimaryKeySelective(user);
		user = userMapper.selectByPrimaryKey(user.getUserId());
		user.setPassword(null);
		redisTemplate.opsForValue().set("loginuser:"+user.getUserId(),user);
		return user;
	}
	
	@Override
	@Transactional
	public User upPic(Long userId, String picUrl){
		User user = new User();
		user.setUserId(userId);
		user.setPic(picUrl);
		userMapper.updateByPrimaryKeySelective(user);
		user = userMapper.selectByPrimaryKey(userId);
		user.setPassword(null);
		redisTemplate.opsForValue().set("loginuser:"+user.getUserId(),user);
		return user;
	}
	/**
	 * 根据用户名邮箱修改
	 * @param user
	 */
	@Override
	@Transactional
	public void updateUE(User user) {
	userDao.updateByEmailAndUserName(user.getPassword(),user.getEmail(),
			user.getUserName());
	}
	/**
	* 删除
	* @param id
	*/
	@Override
	public void deleteById(Long id) {
		userDao.deleteById(id);
	}

	/**
	* 动态条件构建
	* @param searchMap
	* @return
	*/
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<>();
				
				//用户名
				if (searchMap.get("userId")!=null && !"".equals(searchMap.get(
						"userId"))) {
					predicateList.add(cb.equal(root.get("userId").as(Long.class)
						, (Long)searchMap.get("userId")));
				}
				//用户名
				if (searchMap.get("userName")!=null && !"".equals(searchMap.get("userName"))) {
					predicateList.add(cb.like(root.get("userName").as(String.class), "%"+(String)searchMap.get("userName")+"%"));
				}
				//用户真实姓名
				if (searchMap.get("realName")!=null && !"".equals(searchMap.get("realName"))) {
					predicateList.add(cb.like(root.get("realName").as(String.class), "%"+(String)searchMap.get("realName")+"%"));
				}
				//密码
				if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
					predicateList.add(cb.equal(root.get("password").as(String.class), (String)searchMap.get("password")));
				}
				//qq_openid
				if (searchMap.get("qqOpenid")!=null && !"".equals(searchMap.get("qqOpenid"))) {
					predicateList.add(cb.like(root.get("qqOpenid").as(String.class), "%"+(String)searchMap.get("qqOpenid")+"%"));
				}
				//wx_openid
				if (searchMap.get("wxOpenid")!=null && !"".equals(searchMap.get("wxOpenid"))) {
					predicateList.add(cb.like(root.get("wxOpenid").as(String.class), "%"+(String)searchMap.get("wxOpenid")+"%"));
				}
				//邮箱
				if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
					predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
				}
				//年龄
				if (searchMap.get("age")!=null && !"".equals(searchMap.get("age"))) {
					predicateList.add(cb.equal(root.get("age").as(Integer.class),
							(Integer)searchMap.get("age")));
				}
				//性别 男：1 女：0
				if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
					predicateList.add(cb.like(root.get("sex").as(String.class), (String)searchMap.get("sex")));
				}
				//电话
				if (searchMap.get("phone")!=null && !"".equals(searchMap.get("phone"))) {
					predicateList.add(cb.like(root.get("phone").as(String.class), "%"+(String)searchMap.get("phone")+"%"));
				}
				//头像
				if (searchMap.get("pic")!=null && !"".equals(searchMap.get("pic"))) {
					predicateList.add(cb.like(root.get("pic").as(String.class), "%"+(String)searchMap.get("pic")+"%"));
				}
				//籍贯
				if (searchMap.get("nativePlace")!=null && !"".equals(searchMap.get("nativePlace"))) {
					predicateList.add(cb.like(root.get("nativePlace").as(String.class), "%"+(String)searchMap.get("nativePlace")+"%"));
				}
				//职位
				if (searchMap.get("position")!=null && !"".equals(searchMap.get("position"))) {
					predicateList.add(cb.like(root.get("position").as(String.class), "%"+(String)searchMap.get("position")+"%"));
				}
				//用户默认地址
				if (searchMap.get("defaultAddressId")!=null && !"".equals(searchMap.get("defaultAddressId"))) {
					predicateList.add(cb.like(root.get("defaultAddressId").as(String.class), "%"+(String)searchMap.get("defaultAddressId")+"%"));
				}

				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

	@Override
	public User findOne(User user){
		Example<User> of= Example.of(user);
		Optional<User> one = userDao.findOne(of);
		if ( one.isPresent() ) {
			return one.get();
		}
		return null;
	}
	
	@Override
	public void upPassword(Map<String, Object> pasMes) {
		User user = new User();
		user.setUserId((Long)pasMes.get("userId"));
		user.setPassword((String)pasMes.get("newpassword"));
		userMapper.updateByPrimaryKeySelective(user);
	}
	
	/**
	 * 添加地址
	 */
	@Override
	public Long addAddress(Address address){
		
		Address save = addressDao.save(address);
		return save.getAddressId();
		
	}
	
	
	@Override
	public List<Address> addressAll(Long userId){
		List<Address> byUId = addressDao.findByuId(userId);
		return byUId;
	}
	
	@Override
	public void delAddress(Long aId){
		addressDao.deleteById(aId);
	}
	
	@Override
	public void delCar(Car car){
		carMapper.delete(car);
	}
	
	@Override
	public void addOrder(Car car) {
		Order order = new Order();
		order.setuId(car.getuId());
		order.setgId(car.getgId());
		order.setgNum(car.getgNum());
		order.setStatus(0);
		orderMapper.insert(order);
	}
	
	@Override
	@Transactional
	public void delCarAll(Long userId) {
		carDao.deleteAllByuId(userId);
	}
	
	@Override
	public void addOrderAll(List<Goods> goodsList,Long userId) {
		List<Order> orderList = new ArrayList<>();
		for ( Goods goods : goodsList ) {
			Order order = new Order();
			order.setuId(userId);
			order.setgId(goods.getGoodsId());
			order.setgNum(goods.getgNum());
			order.setStatus(0);
			orderList.add(order);
		}
		orderDao.saveAll(orderList);
	}
	
	@Override
	public long countUserCar(Long userId) {
		Car car = new Car();
		car.setuId(userId);
		long count = carDao.count(Example.of(car));
		return count;
	}
	
	@Override
	public long countUserOrder(Long userId) {
		Order order = new Order();
		order.setuId(userId);
		long count = orderDao.count(Example.of(order));
		return count;
	}
}

package com.lyh.xbiaoshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyh.xbiaoshop.constant.SysConstant;
import com.lyh.xbiaoshop.dao.CarDao;
import com.lyh.xbiaoshop.dao.CollectDao;
import com.lyh.xbiaoshop.dao.GoodsDao;
import com.lyh.xbiaoshop.dao.ProductListDao;
import com.lyh.xbiaoshop.entity.Car;
import com.lyh.xbiaoshop.entity.Collect;
import com.lyh.xbiaoshop.entity.Goods;
import com.lyh.xbiaoshop.entity.ProductList;
import com.lyh.xbiaoshop.mapper.GoodsMapper;
import com.lyh.xbiaoshop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Description goods 服务层
 * @author admin
 * @date 2020-12-06 15:45:33
*/
@Service
public class GoodsServiceImpl extends BaseServiceImpl<Goods
		,Long> implements GoodsService  {

	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private CollectDao collectDao;
	
	@Autowired
	private CarDao carDao;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Autowired
	private ProductListDao productListDao;

	/**
	* 查询全部列表
	* @return
	*/
	@Override
	public List<Goods> findAll() {
		return goodsDao.findAll();
	}

	/**
	* 条件查询+分页
	* @param whereMap
	* @param page
	* @param size
	* @return
	*/
	@Override
	public Page<Goods> findSearch(Map whereMap, int page, int size) {
		Specification<Goods> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return goodsDao.findAll(specification, pageRequest);
	}

	/**
 	* 条件查询
	* @param whereMap
	* @return
	*/
	@Override
	public List<Goods> findSearch(Map whereMap) {
		Specification<Goods> specification = createSpecification(whereMap);
		return goodsDao.findAll(specification);
	}

	/**
	* 根据ID查询实体
	* @param id
	* @return
	*/
	@Override
	public Goods findById(Long id) {
		return goodsDao.findById(id).get();
	}

	/**
	* 增加
	* @param goods
	*/
	@Override
	public void add(Goods goods) {
		goodsDao.save(goods);
	}

	/**
	* 修改
	* @param goods
	*/
	@Override
	public void update(Goods goods) {
		goodsDao.save(goods);
	}

	/**
	* 删除
	* @param id
	*/
	@Override
	public void deleteById(Long id) {
		goodsDao.deleteById(id);
	}

	/**
	* 动态条件构建
	* @param searchMap
	* @return
	*/
	private Specification<Goods> createSpecification(Map searchMap) {

		return new Specification<Goods>() {

			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				//商品价格
				if (searchMap.get("price")!=null && !"".equals(searchMap.get("price"))) {
					predicateList.add(cb.like(root.get("price").as(String.class), "%"+(String)searchMap.get("price")+"%"));
				}
				//商品类型id
				if (searchMap.get("typeId")!=null && !"".equals(searchMap.get("typeId"))) {
					predicateList.add(cb.like(root.get("typeId").as(String.class), "%"+(String)searchMap.get("typeId")+"%"));
				}
				//商品图片
				if (searchMap.get("goodsPhotos")!=null && !"".equals(searchMap.get("goodsPhotos"))) {
					predicateList.add(cb.like(root.get("goodsPhotos").as(String.class), "%"+(String)searchMap.get("goodsPhotos")+"%"));
				}
				//商品发布时间
				if (searchMap.get("publicshTime")!=null && !"".equals(searchMap.get("publicshTime"))) {
					predicateList.add(cb.like(root.get("publicshTime").as(String.class), "%"+(String)searchMap.get("publicshTime")+"%"));
				}
				//商品库存
				if (searchMap.get("inventoryNum")!=null && !"".equals(searchMap.get("inventoryNum"))) {
					predicateList.add(cb.like(root.get("inventoryNum").as(String.class), "%"+(String)searchMap.get("inventoryNum")+"%"));
				}
				//标题
				if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
					predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
				}
				//内容
				if (searchMap.get("content")!=null && !"".equals(searchMap.get("content"))) {
					predicateList.add(cb.like(root.get("content").as(String.class), "%"+(String)searchMap.get("content")+"%"));
				}
				//浏览量
				if (searchMap.get("look")!=null && !"".equals(searchMap.get("look"))) {
					predicateList.add(cb.like(root.get("look").as(String.class), "%"+(String)searchMap.get("look")+"%"));
				}
				//卖出量
				if (searchMap.get("bargainNum")!=null && !"".equals(searchMap.get("bargainNum"))) {
					predicateList.add(cb.like(root.get("bargainNum").as(String.class), "%"+(String)searchMap.get("bargainNum")+"%"));
				}

				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}
	@Override
	public List<Goods> newGoods(){
		return goodsDao.findTop4ByOrderByPublicshTimeDesc();
	}
	
	@Override
	public List<Goods> barGoods(){
		return goodsDao.findTop4ByOrderByBargainNumDesc();
	}
	@Override
	public List<Goods> barGoodsByType(Integer typeid){
		return goodsDao.findTop4ByTypeIdOrderByBargainNumDesc(typeid);
	}
	@Override
	public boolean addCollect(Long uid, Long gid){
		Collect collect = new Collect();
		collect.setgId(gid);
		collect.setuId(uid);
		Example<Collect> of = Example.of(collect);
		if ( collectDao.count(of)>0 ){
			//关注过,来取消关注的
			collectDao.delete(collect);
			return false;
		}else {
			//没有关注过,来关注的
			collectDao.save(collect);
			return true;
		}
	}
	@Override
	@Transactional
	public boolean addLook(Long uid, Long gid){
		goodsDao.upLook(gid);
		Collect collect = new Collect();
		collect.setgId(gid);
		collect.setuId(uid);
		Example<Collect> of = Example.of(collect);
		if ( collectDao.count(of)>0 ){
			//关注过
			return true;
		}else {
			//没有关注过
			return false;
		}
	}
	@Override
	@Transactional
	public void addCar(Car car){
		Integer num=car.getgNum();
		car.setgNum(null);
		Example<Car> of = Example.of(car);
		if ( carDao.count(of)>0 ){
			//已经存在，数量加num
			carDao.caraddnum(car.getuId(),car.getgId(),num);
			
		}else {
			//不存在，添加
			car.setgNum(num);
			carDao.save(car);
		}
	}
	
	@Override
	public PageInfo<Goods> findCollect(Long uid,Integer page){
		PageHelper.startPage(page, SysConstant.COLLECT_PAGESIZE);
		List<Goods> goods = goodsMapper.collectGoods(uid);
		return new PageInfo<>(goods);
	}
	@Override
	public List<Goods> findCars(Long uId){
		List<Goods> goods = goodsMapper.selectCars(uId);
		return goods;
	}
	@Override
	public List<ProductList> listAll(){
		List<ProductList> all = productListDao.findAll();
		return all;
	}
	
	@Override
	public PageInfo<Goods> findHotList(Integer page) {
		PageHelper.startPage(page,SysConstant.LIST_PAGESIZE);
		List<Goods> allByOrderByLookDesc = goodsMapper.findAllByOrderByLookDesc();
		return new PageInfo<>(allByOrderByLookDesc);
	}
	
	@Override
	public PageInfo<Goods> findAffordableList(Integer page) {
		PageHelper.startPage(page,SysConstant.LIST_PAGESIZE);
		List<Goods> allByOrderByPriceDesc = goodsMapper.findAllByOrderByPriceDesc();
		return new PageInfo<>(allByOrderByPriceDesc);
	}
	
	
	@Override
	public PageInfo<Goods> findByType(Integer page,Integer typeId) {
		PageHelper.startPage(page,SysConstant.LIST_PAGESIZE);
		List<Goods> allByTypeId = goodsMapper.findAllByTypeId(typeId);
		return new PageInfo<>(allByTypeId);
	}
	
	@Override
	public PageInfo<Goods> findSearchList(Integer page, String message) {
		
		PageHelper.startPage(page,SysConstant.LIST_PAGESIZE);
		List<Goods> search = goodsMapper.findSearch(message);
		return new PageInfo<>(search);
	}
}

package com.lyh.xbiaoshop.service.impl;

import com.lyh.xbiaoshop.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public abstract class BaseServiceImpl<T,ID> implements BaseService<T,ID> {
	
	@Autowired
	Mapper<T> mapper;
	
	@Override
	public int deleteByPrimaryKey(ID o) {
		return mapper.deleteByPrimaryKey(o);
	}
	
	@Override
	public int delete(T appVersion) {
		return mapper.delete(appVersion);
	}
	
	@Override
	public int insert(T appVersion) {
		return mapper.insert(appVersion);
	}
	
	@Override
	public int insertSelective(T appVersion) {
		return mapper.insertSelective(appVersion);
	}
	
	@Override
	public boolean existsWithPrimaryKey(ID o) {
		return mapper.existsWithPrimaryKey(o);
	}
	
	@Override
	public List<T> selectAll() {
		return mapper.selectAll();
	}
	
	@Override
	public T selectByPrimaryKey(ID o) {
		return mapper.selectByPrimaryKey(o);
	}
	
	@Override
	public int selectCount(T appVersion) {
		return mapper.selectCount(appVersion);
	}
	
	@Override
	public List<T> select(T appVersion) {
		return mapper.select(appVersion);
	}
	
	@Override
	public T selectOne(T appVersion) {
		return mapper.selectOne(appVersion);
	}
	
	@Override
	public int updateByPrimaryKey(T appVersion) {
		return mapper.updateByPrimaryKey(appVersion);
	}
	
	@Override
	public int updateByPrimaryKeySelective(T appVersion) {
		return mapper.updateByPrimaryKeySelective(appVersion);
	}
}

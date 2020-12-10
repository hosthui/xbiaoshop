package com.lyh.xbiaoshop.service;

import java.util.List;

public interface BaseService<T,ID> {
	int deleteByPrimaryKey(ID o);
	
	
	int delete(T appVersion);
	
	
	int insert(T appVersion);
	
	
	int insertSelective(T appVersion);
	
	
	boolean existsWithPrimaryKey(ID o);
	
	
	List<T> selectAll();
	
	
	T selectByPrimaryKey(ID o);
	
	
	int selectCount(T appVersion);
	
	
	List<T> select(T appVersion);
	
	
	T selectOne(T appVersion);
	
	
	int updateByPrimaryKey(T appVersion);
	
	
	int updateByPrimaryKeySelective(T appVersion);
}

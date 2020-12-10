package com.lyh.xbiaoshop.dao;

import com.lyh.xbiaoshop.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @Description car 数据访问接口
 * @date 2020-12-06 18:41:12
*/
public interface CarDao extends JpaRepository<Car,Long>,
		JpaSpecificationExecutor<Car>{
	
	
	@Query(value = "update car set g_num=g_num+?3 where u_id=?1 and g_id=?2",
			nativeQuery =
			true)
	@Modifying
	void caraddnum(Long uid,Long aid,Integer num);
	
	
	@Query(value = "delete from car where u_id=?1",nativeQuery = true)
	@Modifying
	void deleteAllByuId(Long userId);
	
}


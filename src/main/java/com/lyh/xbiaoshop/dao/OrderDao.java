package com.lyh.xbiaoshop.dao;

import com.lyh.xbiaoshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


/**
 * @Description order 数据访问接口
 * @date 2020-12-08 16:27:45
*/
public interface OrderDao extends JpaRepository<Order,Long>,
		JpaSpecificationExecutor<Order>{
	
	@Query(value = "update tb_order set status=?2 where order_id=?1",
			nativeQuery = true)
	@Modifying
	void updateOrderByOrderId(Long oId,Integer status);
	@Query(value = "update tb_order set status=?2 where u_id=?1 and " +
			"status=0",
			nativeQuery = true)
	@Modifying
	void updateOrderByUserIdAndStatus(Long userId, Integer status);
}


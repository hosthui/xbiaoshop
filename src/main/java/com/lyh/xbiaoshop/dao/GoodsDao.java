package com.lyh.xbiaoshop.dao;

import com.lyh.xbiaoshop.entity.Goods;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @Description goods 数据访问接口
 * @date 2020-12-06 15:45:32
*/
public interface GoodsDao extends JpaRepository<Goods,Long>,
		JpaSpecificationExecutor<Goods>{
		
	
	List<Goods> findTop4ByOrderByPublicshTimeDesc();
	
	List<Goods> findTop4ByOrderByBargainNumDesc();
	
	List<Goods> findTop4ByTypeIdOrderByBargainNumDesc(Integer typeid);
	
	List<Goods> findAllByOrderByLookDesc(Pageable pageable);
	
	
	@Query(value = "update goods set look=look+1 where goods_id=?1",
			nativeQuery = true)
	@Modifying
	void upLook(Long gid);
}


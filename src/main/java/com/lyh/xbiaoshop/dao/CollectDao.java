package com.lyh.xbiaoshop.dao;

import com.lyh.xbiaoshop.entity.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



/**
 * @Description collect 数据访问接口
 * @date 2020-12-06 17:39:12
*/
public interface CollectDao extends JpaRepository<Collect,Long>,
		JpaSpecificationExecutor<Collect>{

}


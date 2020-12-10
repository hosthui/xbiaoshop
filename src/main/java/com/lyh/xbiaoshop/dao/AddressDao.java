package com.lyh.xbiaoshop.dao;

import com.lyh.xbiaoshop.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


/**
 * @Description address 数据访问接口
 * @date 2020-12-07 21:06:38
*/
public interface AddressDao extends JpaRepository<Address,Long>,
		JpaSpecificationExecutor<Address>{
	List<Address> findByuId(Long userid);
}


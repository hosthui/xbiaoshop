package com.lyh.xbiaoshop.dao;

import com.lyh.xbiaoshop.entity.ProductList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



/**
 * @Description product_list 数据访问接口
 * @date 2020-12-09 17:57:28
*/
public interface ProductListDao extends JpaRepository<ProductList,Long>,
		JpaSpecificationExecutor<ProductList>{

}


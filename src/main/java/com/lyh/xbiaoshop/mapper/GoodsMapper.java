package com.lyh.xbiaoshop.mapper;

import com.lyh.xbiaoshop.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface GoodsMapper extends Mapper<Goods> {
	
	
	@Select("SELECT g.* FROM  goods g,collect c WHERE g.goods_id=c.g_id AND c" +
			".u_id=#{uid}")
	List<Goods> collectGoods(Long uid);
	
	@Select("SELECT g.*,c.g_num FROM goods g,car c WHERE c.g_id=g.goods_id and c" +
			".u_id=#{uid}")
	List<Goods> selectCars(Long uid);
	
	@Select("SELECT g.*,o.g_num,o.order_id FROM goods g,tb_order o WHERE o.g_id=g" +
			".goods_id and o.u_id=#{userId} AND o.status=#{status}")
	List<Goods> selectOrders(@Param("userId") Long userId,@Param("status")  Integer status);
	
	@Select("SELECT * FROM goods ORDER BY look DESC")
	List<Goods> findAllByOrderByLookDesc();
	
	@Select("SELECT * FROM goods ORDER BY price")
	List<Goods> findAllByOrderByPriceDesc();
	
	@Select("SELECT * FROM goods WHERE type_id=#{typeId}")
	List<Goods> findAllByTypeId(Integer typeId);
	
	@SelectProvider(type = GoodsMapperSqlProvider.class,method = "searchSql")
	List<Goods> findSearch(String message);
}

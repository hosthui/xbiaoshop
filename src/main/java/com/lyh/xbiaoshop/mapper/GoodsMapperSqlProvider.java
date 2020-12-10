package com.lyh.xbiaoshop.mapper;

public class GoodsMapperSqlProvider {
	
	public String searchSql(String message){
		StringBuffer buffer = new StringBuffer("SELECT * FROM goods ");
		if ( message!=null||!"".equals(message) ){
			buffer.append("WHERE title LIKE CONCAT('%',#{message},'%') or " +
					"content LIKE" +
					" CONCAT('%',#{message},'%')");
		}
		return buffer.toString();
	}
}

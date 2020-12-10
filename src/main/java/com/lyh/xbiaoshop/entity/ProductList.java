package com.lyh.xbiaoshop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description product_list实体类
 * @author admin
 * @date 2020-12-09 17:57:29
 */
@Entity
@Table(name="product_list")
public class ProductList implements Serializable{

	@Id
	private Long listId;//主键ID

	private String listName; //商品列表名称
	private String listUrl; //商品列表地址
	
	public Long getListId() {
		return listId;
	}
	
	public void setListId(Long listId) {
		this.listId = listId;
	}
	
	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getListName() {
		return this.listName;
	}
	public void setListUrl(String listUrl) {
		this.listUrl = listUrl;
	}

	public String getListUrl() {
		return this.listUrl;
	}

}




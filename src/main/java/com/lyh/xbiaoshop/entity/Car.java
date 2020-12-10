package com.lyh.xbiaoshop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description car实体类
 * @author admin
 * @date 2020-12-06 18:41:13
 */
@Entity
@Table(name="car")
@IdClass(Car.class)
public class Car implements Serializable{

	@Id
	private Long uId;//主键ID
	
	@Id
	private Long gId;

	private Integer gNum; //商品数量
	
	public Long getuId() {
		return uId;
	}
	
	public void setuId(Long uId) {
		this.uId = uId;
	}
	
	public Long getgId() {
		return gId;
	}
	
	public void setgId(Long gId) {
		this.gId = gId;
	}
	
	public Integer getgNum() {
		return gNum;
	}
	
	public void setgNum(Integer gNum) {
		this.gNum = gNum;
	}
}




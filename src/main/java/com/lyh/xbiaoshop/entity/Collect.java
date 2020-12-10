package com.lyh.xbiaoshop.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description collect实体类
 * @author admin
 * @date 2020-12-06 17:39:12
 */
@Entity
@IdClass(Collect.class)
@Table(name="collect")
public class Collect implements Serializable{

	@Id
	private Long uId;//主键ID
	@Id
	private Long gId;
	
	
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
}




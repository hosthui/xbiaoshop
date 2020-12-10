package com.lyh.xbiaoshop.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description order实体类
 * @author admin
 * @date 2020-12-08 16:27:45
 */
@Entity
@Table(name="tb_order")
public class Order implements Serializable{
	
	//order_id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	private Long uId;//主键ID
	
	private Long gId;
	
	private Integer gNum; //商品数量
	private Integer status; //定单状态  1：已支付 2：待评价
	
	public Long getuId() {
		return uId;
	}
	
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
}




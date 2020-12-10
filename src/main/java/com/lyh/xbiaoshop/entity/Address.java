package com.lyh.xbiaoshop.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description address实体类
 * @author admin
 * @date 2020-12-07 21:06:38
 */
@Entity
@Table(name="address")
public class Address implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;//主键ID

	private Long uId; //用户id
	private String consignee; //收件人
	private String address; //所在地区
	private String detAddress; //详细地址
	private String phone; //电话
	private String postcode; //邮编
	
	public Long getAddressId() {
		return addressId;
	}
	
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	
	public Long getuId() {
		return uId;
	}
	
	public void setuId(Long uId) {
		this.uId = uId;
	}
	
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsignee() {
		return this.consignee;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}
	public void setDetAddress(String detAddress) {
		this.detAddress = detAddress;
	}

	public String getDetAddress() {
		return this.detAddress;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPostcode() {
		return this.postcode;
	}

}




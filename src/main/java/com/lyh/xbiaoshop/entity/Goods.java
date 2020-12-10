package com.lyh.xbiaoshop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @Description goods实体类
 * @author admin
 * @date 2020-12-06 15:45:33
 */
@Entity
@Table(name="goods")
public class Goods implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long goodsId;//主键ID

	private Integer price; //商品价格
	private Integer typeId; //商品类型id
	private String goodsPhotos; //商品图片
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date publicshTime; //商品发布时间
	private Integer inventoryNum; //商品库存
	private String title; //标题
	private String content; //内容
	private Integer look; //浏览量
	private Integer bargainNum; //卖出量
	
	
	@Transient
	private Integer gNum;
	@Transient
	private Long orderId;
	//order_id
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public Integer getgNum() {
		return gNum;
	}
	
	public void setgNum(Integer gNum) {
		this.gNum = gNum;
	}
	
	public Long getGoodsId() {
		return goodsId;
	}
	
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getPrice() {
		return this.price;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getTypeId() {
		return this.typeId;
	}
	public void setGoodsPhotos(String goodsPhotos) {
		this.goodsPhotos = goodsPhotos;
	}

	public String getGoodsPhotos() {
		String[] split = this.goodsPhotos.split(",");
		StringBuffer buffer = new StringBuffer();
		InetAddress localHost=null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		for ( int i = 0; i < split.length; i++ ) {
			buffer.append(split[i].replace("localhost",
					localHost.getHostAddress())+",");
		}
		buffer.deleteCharAt(buffer.length()-1);
		return buffer.toString();
	}
	public void setPublicshTime(java.util.Date publicshTime) {
		this.publicshTime = publicshTime;
	}

	public java.util.Date getPublicshTime() {
		return this.publicshTime;
	}
	public void setInventoryNum(Integer inventoryNum) {
		this.inventoryNum = inventoryNum;
	}

	public Integer getInventoryNum() {
		return this.inventoryNum;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}
	public void setLook(Integer look) {
		this.look = look;
	}

	public Integer getLook() {
		return this.look;
	}
	public void setBargainNum(Integer bargainNum) {
		this.bargainNum = bargainNum;
	}

	public Integer getBargainNum() {
		return this.bargainNum;
	}

}




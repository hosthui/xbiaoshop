package com.lyh.xbiaoshop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @Description user实体类
 * @author admin
 * @date 2020-12-06 10:30:30
 */
@Entity
@Table(name="user")
public class User implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;//主键ID

	private String userName; //用户名
	private String realName; //用户真实姓名
	private String password; //密码
	private String qqOpenid; //qq_openid
	private String wxOpenid; //wx_openid
	private String email; //邮箱
	private Integer age; //年龄
	private String sex; //性别 男：1 女：0
	private String phone; //电话
	private String pic; //头像
	private String nativePlace; //籍贯
	private String position; //职位
	private Long defaultAddressId; //用户默认地址
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date registerTime;
	
	
	public Date getRegisterTime() {
		return registerTime;
	}
	
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRealName() {
		return this.realName;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}
	public void setQqOpenid(String qqOpenid) {
		this.qqOpenid = qqOpenid;
	}

	public String getQqOpenid() {
		return this.qqOpenid;
	}
	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}

	public String getWxOpenid() {
		return this.wxOpenid;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}
	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return this.age;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPic() {
		InetAddress localHost=null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if ( this.pic!=null ){
			return this.pic.replace("localhost",
					localHost.getHostAddress());
		}
		return this.pic;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getNativePlace() {
		return this.nativePlace;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	public String getPosition() {
		return this.position;
	}
	
	public Long getDefaultAddressId() {
		return defaultAddressId;
	}
	
	public void setDefaultAddressId(Long defaultAddressId) {
		this.defaultAddressId = defaultAddressId;
	}
}




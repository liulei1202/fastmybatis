package com.myapp.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.myapp.entity.type.UserInfoType;

/**
  表:user_info 用户信息表
*/
public class UserInfo {

	// 自增主键
	@Id
	@GeneratedValue(strategy=
        	GenerationType.IDENTITY
        )
	private Integer id;
	// t_user外键
	private Integer userId;
	// 城市
	private String city;
	// 街道
	private String address;
	// 添加时间
	private Date createTime;
	
	private UserInfoType status;
	

	/** 设置 自增主键,对应字段 user_info.id */
	public void setId(Integer id){
		this.id = id;
	}
	/** 获取 自增主键,对应字段 user_info.id */
	public Integer getId(){
		return this.id;
	}

	/** 设置 t_user外键,对应字段 user_info.user_id */
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	/** 获取 t_user外键,对应字段 user_info.user_id */
	public Integer getUserId(){
		return this.userId;
	}

	/** 设置 城市,对应字段 user_info.city */
	public void setCity(String city){
		this.city = city;
	}
	/** 获取 城市,对应字段 user_info.city */
	public String getCity(){
		return this.city;
	}

	/** 设置 街道,对应字段 user_info.address */
	public void setAddress(String address){
		this.address = address;
	}
	/** 获取 街道,对应字段 user_info.address */
	public String getAddress(){
		return this.address;
	}

	/** 设置 添加时间,对应字段 user_info.create_time */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/** 获取 添加时间,对应字段 user_info.create_time */
	public Date getCreateTime(){
		return this.createTime;
	}
	

	public UserInfoType getStatus() {
		return status;
	}
	public void setStatus(UserInfoType status) {
		this.status = status;
	}
	
    @Override
	public String toString() {
    	StringBuilder sb = new StringBuilder();
        sb.append("UserInfo [");
        		        sb.append("id=").append(id);
        		sb.append(", ");        sb.append("userId=").append(userId);
        		sb.append(", ");        sb.append("city=").append(city);
        		sb.append(", ");        sb.append("address=").append(address);
        		sb.append(", ");        sb.append("createTime=").append(createTime);
                sb.append("]");
		return sb.toString();
	}

}
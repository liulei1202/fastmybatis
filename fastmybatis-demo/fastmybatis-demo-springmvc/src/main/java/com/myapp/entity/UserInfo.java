package com.myapp.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 表名：user_info 备注：InnoDB free: 11264 kB
 */
@Table(name = "user_info")
public class UserInfo {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/** 数据库字段：id */
	private Integer id;

	// 一对一配置，这里的user_id对应TUser主键
	// 触发懒加载时，会拿user_id的值去查询t_user表
	// 即：SELECT * FROM t_user WHERE id={user_id}
	@Column(name = "user_id")
	private TUser user;

	/** 地址, 数据库字段：address */
	private String address;

	/** 数据库字段：user_info.id */
	public void setId(Integer id) {
		this.id = id;
	}

	/** 数据库字段：user_info.id */
	public Integer getId() {
		return this.id;
	}

	/** 设置地址, 数据库字段：user_info.address */
	public void setAddress(String address) {
		this.address = address;
	}

	/** 获取地址, 数据库字段：user_info.address */
	public String getAddress() {
		return this.address;
	}

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		UserInfo other = (UserInfo) obj;

		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("UserInfo [");
		sb.append("id=").append(id);
		sb.append(", ");
		sb.append("address=").append(address);
		sb.append("]");

		return sb.toString();
	}

}

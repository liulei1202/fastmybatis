package com.myapp.entity;

import com.gitee.fastmybatis.core.annotation.LazyFetch;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：user_info
 * 备注：用户信息表; InnoDB free: 11264 kB
 */
@Table(name = "user_info")
public class UserInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** 自增主键, 数据库字段：id */
    private Integer id;

    /** t_user外键, 数据库字段：user_id */
    private Integer userId;
    
    // 一对一配置，这里的user_id对应TUser主键
 	// 触发懒加载时，会拿user_id的值去查询t_user表
 	// 即：SELECT * FROM t_user WHERE id={user_id}
 	@LazyFetch(column = "user_id")
 	private TUser user;

    /** 城市, 数据库字段：city */
    private String city;

    /** 街道, 数据库字段：address */
    private String address;

    /** 类型, 数据库字段：status */
    private String status;

    /** 添加时间, 数据库字段：create_time */
    private Date createTime;

    /** 设置自增主键, 数据库字段：user_info.id */
    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取自增主键, 数据库字段：user_info.id */
    public Integer getId() {
        return this.id;
    }

    /** 设置t_user外键, 数据库字段：user_info.user_id */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /** 获取t_user外键, 数据库字段：user_info.user_id */
    public Integer getUserId() {
        return this.userId;
    }

    /** 设置城市, 数据库字段：user_info.city */
    public void setCity(String city) {
        this.city = city;
    }

    /** 获取城市, 数据库字段：user_info.city */
    public String getCity() {
        return this.city;
    }

    /** 设置街道, 数据库字段：user_info.address */
    public void setAddress(String address) {
        this.address = address;
    }

    /** 获取街道, 数据库字段：user_info.address */
    public String getAddress() {
        return this.address;
    }

    /** 设置类型, 数据库字段：user_info.status */
    public void setStatus(String status) {
        this.status = status;
    }

    /** 获取类型, 数据库字段：user_info.status */
    public String getStatus() {
        return this.status;
    }

    /** 设置添加时间, 数据库字段：user_info.create_time */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 获取添加时间, 数据库字段：user_info.create_time */
    public Date getCreateTime() {
        return this.createTime;
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
        sb.append("userId=").append(userId);
        sb.append(", ");
        sb.append("city=").append(city);
        sb.append(", ");
        sb.append("address=").append(address);
        sb.append(", ");
        sb.append("status=").append(status);
        sb.append(", ");
        sb.append("createTime=").append(createTime);
        sb.append("]");

        return sb.toString();
    }

	public TUser getUser() {
		return user;
	}

	public void setUser(TUser user) {
		this.user = user;
	}
    
    
}

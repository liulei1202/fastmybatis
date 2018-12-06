package com.myapp.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：t_user
 * 备注：用户表
 */
@Table(name = "t_user")
public class TUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /** ID, 数据库字段：id */
    private Integer id;

    /** 用户名, 数据库字段：username */
    private String username;

    /** 状态, 数据库字段：state */
    private Byte state;

    /** 是否删除, 数据库字段：isdel */
    @com.gitee.fastmybatis.core.annotation.LogicDelete
    private Boolean isdel;

    /** 备注, 数据库字段：remark */
    private String remark;

    /** 添加时间, 数据库字段：add_time */
    private Date addTime;

    /** 金额, 数据库字段：money */
    private BigDecimal money;

    /** 剩下的钱, 数据库字段：left_money */
    private Float leftMoney;
    
    /** 设置ID,数据库字段：t_user.id */
    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取ID,数据库字段：t_user.id */
    public Integer getId() {
        return this.id;
    }

    /** 设置用户名,数据库字段：t_user.username */
    public void setUsername(String username) {
        this.username = username;
    }

    /** 获取用户名,数据库字段：t_user.username */
    public String getUsername() {
        return this.username;
    }

    /** 设置状态,数据库字段：t_user.state */
    public void setState(Byte state) {
        this.state = state;
    }

    /** 获取状态,数据库字段：t_user.state */
    public Byte getState() {
        return this.state;
    }

    /** 设置是否删除,数据库字段：t_user.isdel */
    public void setIsdel(Boolean isdel) {
        this.isdel = isdel;
    }

    /** 获取是否删除,数据库字段：t_user.isdel */
    public Boolean getIsdel() {
        return this.isdel;
    }

    /** 设置备注,数据库字段：t_user.remark */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** 获取备注,数据库字段：t_user.remark */
    public String getRemark() {
        return this.remark;
    }

    /** 设置添加时间,数据库字段：t_user.add_time */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /** 获取添加时间,数据库字段：t_user.add_time */
    public Date getAddTime() {
        return this.addTime;
    }

    /** 设置金额,数据库字段：t_user.money */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /** 获取金额,数据库字段：t_user.money */
    public BigDecimal getMoney() {
        return this.money;
    }

    /** 设置剩下的钱,数据库字段：t_user.left_money */
    public void setLeftMoney(Float leftMoney) {
        this.leftMoney = leftMoney;
    }

    /** 获取剩下的钱,数据库字段：t_user.left_money */
    public Float getLeftMoney() {
        return this.leftMoney;
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

        TUser other = (TUser) obj;

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
        sb.append("TUser [");
        sb.append("id=").append(id);
        sb.append(", ");
        sb.append("username=").append(username);
        sb.append(", ");
        sb.append("state=").append(state);
        sb.append(", ");
        sb.append("isdel=").append(isdel);
        sb.append(", ");
        sb.append("remark=").append(remark);
        sb.append(", ");
        sb.append("addTime=").append(addTime);
        sb.append(", ");
        sb.append("money=").append(money);
        sb.append(", ");
        sb.append("leftMoney=").append(leftMoney);
        sb.append("]");

        return sb.toString();
    }
}

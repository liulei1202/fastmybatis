package com.myapp.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：address
 *
 * @author fastmybatis-generator
 */
@Table(name = "address")
public class Address {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**  数据库字段：id */
    private String id;

    /**  数据库字段：address */
    private String address;

    /**  数据库字段：address.id */
    public void setId(String id) {
        this.id = id;
    }

    /**  数据库字段：address.id */
    public String getId() {
        return this.id;
    }

    /**  数据库字段：address.address */
    public void setAddress(String address) {
        this.address = address;
    }

    /**  数据库字段：address.address */
    public String getAddress() {
        return this.address;
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

        Address other = (Address) obj;

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
        sb.append("Address [");
        sb.append("id=").append(id);
        sb.append(", ");
        sb.append("address=").append(address);
        sb.append("]");

        return sb.toString();
    }
}

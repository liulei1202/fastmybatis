package com.myapp.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "address")
public class Address extends StringId{

//	@Id
//	@GeneratedValue(generator = "system-uuid")
//	private String id;

	@Column(name = "address")
	private String address;

//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getId() {
//		return this.id;
//	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Address [");
		sb.append("id=").append(getId());
		sb.append(", ");
		sb.append("address=").append(address);
		sb.append("]");
		return sb.toString();
	}

}
package com.gitee.fastmybatis.generator.entity;

public class DataSourceConfig extends DataBaseConfig {
	private int dcId;
	private String backUser;

	public int getDcId() {
		return dcId;
	}

	public void setDcId(int dcId) {
		this.dcId = dcId;
	}

	public String getBackUser() {
		return backUser;
	}

	public void setBackUser(String backUser) {
		this.backUser = backUser;
	}

}

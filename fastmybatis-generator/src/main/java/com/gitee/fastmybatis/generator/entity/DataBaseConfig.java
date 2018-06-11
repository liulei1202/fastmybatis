package com.gitee.fastmybatis.generator.entity;

import java.util.Map;

public class DataBaseConfig {

	private String dbName;
	private String driverClass;
	private String jdbcUrl;
	private String username;
	private String password;
	private boolean uuid;

	private boolean showSchema = true;
	
	private Map<Object,Object> param = null;

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public boolean isShowSchema() {
		return showSchema;
	}

	public void setShowSchema(boolean showSchema) {
		this.showSchema = showSchema;
	}

	public boolean isUuid() {
		return uuid;
	}

	public void setUuid(boolean uuid) {
		this.uuid = uuid;
	}

    public Map<Object, Object> getParam() {
        return param;
    }

    public void setParam(Map<Object, Object> param) {
        this.param = param;
    }
	
}

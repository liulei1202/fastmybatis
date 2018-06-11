package com.gitee.fastmybatis.generator.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class ClientParam {

	private List<String> tplList;

	private String tableName;
	private String packageName;
	private String charset = "UTF-8";

	private String dbName;
	private String driverClass;
	private String jdbcUrl;
	private String username;
	private String password;
	private boolean showSchema;
	private boolean uuid;
	
	private Map<Object,Object> param = null;

	public DataBaseConfig buildDataBaseConfig() {
		DataBaseConfig config = new DataBaseConfig();
		try {
			BeanUtils.copyProperties(config, this);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return config;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

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

	public List<String> getTplList() {
		return tplList;
	}

	public void setTplList(List<String> tplList) {
		this.tplList = tplList;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
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

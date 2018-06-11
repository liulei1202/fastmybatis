package com.gitee.fastmybatis.core.query;

/**
 * @author tanghc
 */
public class Column {
	private String columnName;
	private String alias;

	public Column(String columnName, String alias) {
		super();
		this.columnName = columnName;
		this.alias = alias;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getTypeHandler() {
		return "";
	}

}

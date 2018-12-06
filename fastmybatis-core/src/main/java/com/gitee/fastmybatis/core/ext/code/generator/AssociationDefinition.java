package com.gitee.fastmybatis.core.ext.code.generator;

/**
 * @author tanghc
 */
public class AssociationDefinition {
	private String property;
	private String column;
	private String select;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

}

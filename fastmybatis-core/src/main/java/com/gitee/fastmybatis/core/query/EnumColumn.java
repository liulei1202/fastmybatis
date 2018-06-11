package com.gitee.fastmybatis.core.query;

/**
 * @author tanghc
 */
public class EnumColumn extends Column {

	private static String TYPE_HANDLER = ", typeHandler=com.gitee.fastmybatis.core.handler.EnumTypeHandler";

	public EnumColumn(String columnName, String alias) {
		super(columnName, alias);
	}

	@Override
	public String getTypeHandler() {
		return TYPE_HANDLER;
	}
}

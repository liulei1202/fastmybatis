package com.gitee.fastmybatis.core.query.expression;

import com.gitee.fastmybatis.core.SqlConsts;

/**
 * 拼接SQL语句
 * 
 * @author tanghc
 * 
 */
public class SqlExpression implements ExpressionSqlable {

	private String joint = SqlConsts.AND;
	private String sql;
	private int index = DEFAULT_INDEX;

	public SqlExpression(String sql) {
		this.sql = sql;
	}
	
	public SqlExpression(String joint,String sql) {
		this.joint = joint;
		this.sql = sql;
	}

	@Override
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public String getJoint() {
		return joint;
	}

	public void setJoint(String joint) {
		this.joint = joint;
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public int index() {
		return index;
	}
}

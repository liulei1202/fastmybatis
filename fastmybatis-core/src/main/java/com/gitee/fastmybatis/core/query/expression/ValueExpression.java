package com.gitee.fastmybatis.core.query.expression;

import com.gitee.fastmybatis.core.SqlConsts;

/**
 * 值查询
 * 
 * @author tanghc
 */
public class ValueExpression implements ExpressionValueable {

	private String column = "";
	private String equal = SqlConsts.EQUAL;
	private Object value;
	private String joint = SqlConsts.AND;
	private int index = DEFAULT_INDEX;

	public ValueExpression(String column, Object value) {
		if (value == null) {
			throw new NullPointerException("ValueExpression(String column, Object value)中value不能为null.");
		}
		this.column = column;
		this.value = value;
	}

	public ValueExpression(String column, String equal, Object value) {
		this(column, value);
		this.equal = equal;
	}

	public ValueExpression(String joint, String column, String equal, Object value) {
		this(column, equal, value);
		this.joint = joint;
	}

	@Override
	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	@Override
	public String getEqual() {
		return equal;
	}

	public void setEqual(String equal) {
		this.equal = equal;
	}

	@Override
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
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

	@Override
	public String toString() {
		return this.joint + " " + this.column + this.equal + this.value;
	}
}

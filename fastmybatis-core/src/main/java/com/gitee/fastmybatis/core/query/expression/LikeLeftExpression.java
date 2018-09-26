package com.gitee.fastmybatis.core.query.expression;


/**
 * Like条件查询,左边模糊匹配,即'%aaa'
 * @author tanghc
 *
 */
public class LikeLeftExpression extends AbstractLikeExpression {


	/**
	 * Like条件查询,左边模糊匹配,即'%aaa'
	 * @param column 数据库字段名
	 * @param value 查询的值
	 */
	public LikeLeftExpression(String column, Object value) {
		super(column, value);
	}

	/**
	 * Like条件查询,左边模糊匹配,即'%aaa'
	 * @param joint 连接符，and，or
	 * @param column 数据库字段名
	 * @param value 查询的值
	 */
	public LikeLeftExpression(String joint, String column, Object value) {
		super(joint, column, value);
	}
	
	@Override
	public Object getValue() {
		return "%" + super.getValue();
	}
	
	
}

package com.gitee.fastmybatis.core.query.expression.builder.factory;

/**
 * @author tanghc
 */
public class LikeRightExpressionFactory extends LikeExpressionFactory {

	@Override
	protected String getValue(Object value) {
		return value + "%";
	}

}

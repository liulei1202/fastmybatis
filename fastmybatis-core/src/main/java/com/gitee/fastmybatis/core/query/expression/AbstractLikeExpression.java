package com.gitee.fastmybatis.core.query.expression;

import com.gitee.fastmybatis.core.SqlConsts;

/**
 * @author tanghc
 */
public abstract class AbstractLikeExpression extends ValueExpression {

	public AbstractLikeExpression(String column, Object value) {
		super(column, value);
	}

	public AbstractLikeExpression(String joint, String column, Object value) {
		super(joint, column, SqlConsts.LIKE, value);
	}

	@Override
	public String getEqual() {
		return SqlConsts.LIKE;
	}

}

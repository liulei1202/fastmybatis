package com.gitee.fastmybatis.core.query.expression.builder.factory;

import com.gitee.fastmybatis.core.query.Joint;
import com.gitee.fastmybatis.core.query.Operator;
import com.gitee.fastmybatis.core.query.expression.Expression;
import com.gitee.fastmybatis.core.query.expression.ValueExpression;

/**
 * @author tanghc
 */
public class ValueExpressionFactory implements ExpressionFactory {

	@Override
	public Expression buildExpression(Joint joint, String columnName, Operator operator, Object value) {
		return new ValueExpression(joint.getJoint(), columnName, operator.getOperator(), value);
	}

}

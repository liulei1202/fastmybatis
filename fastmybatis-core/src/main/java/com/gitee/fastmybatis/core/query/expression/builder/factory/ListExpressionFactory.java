package com.gitee.fastmybatis.core.query.expression.builder.factory;

import java.util.Collection;

import com.gitee.fastmybatis.core.query.Joint;
import com.gitee.fastmybatis.core.query.Operator;
import com.gitee.fastmybatis.core.query.expression.Expression;
import com.gitee.fastmybatis.core.query.expression.ListExpression;

/**
 * @author tanghc
 */
public class ListExpressionFactory implements ExpressionFactory {
	
	@Override
	public Expression buildExpression(Joint joint, String columnName, Operator operator, Object value) {
		Expression expression = null;
		if (value.getClass().isArray()) {
			expression = new ListExpression(joint.getJoint(), columnName, operator.getOperator(), (Object[]) value);
		} else if (value instanceof Collection) {
			expression = new ListExpression(joint.getJoint(), columnName, operator.getOperator(), (Collection<?>) value);
		}
		return expression;
	}


}

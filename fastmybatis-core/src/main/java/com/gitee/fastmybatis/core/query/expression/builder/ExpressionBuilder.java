package com.gitee.fastmybatis.core.query.expression.builder;

import java.util.HashMap;
import java.util.Map;

import com.gitee.fastmybatis.core.query.Operator;
import com.gitee.fastmybatis.core.query.annotation.Condition;
import com.gitee.fastmybatis.core.query.expression.Expression;
import com.gitee.fastmybatis.core.query.expression.builder.factory.ExpressionFactory;
import com.gitee.fastmybatis.core.query.expression.builder.factory.LikeExpressionFactory;
import com.gitee.fastmybatis.core.query.expression.builder.factory.LikeLeftExpressionFactory;
import com.gitee.fastmybatis.core.query.expression.builder.factory.LikeRightExpressionFactory;
import com.gitee.fastmybatis.core.query.expression.builder.factory.ListExpressionFactory;
import com.gitee.fastmybatis.core.query.expression.builder.factory.ValueExpressionFactory;
import com.gitee.fastmybatis.core.util.ClassUtil;

/**
 * 负责构建条件表达式
 * @author tanghc
 */
public class ExpressionBuilder {
	private static Map<Operator, ExpressionFactory> factoryMap = new HashMap<>();
	static {
		factoryMap.put(Operator.eq, new ValueExpressionFactory());
		factoryMap.put(Operator.notEq, new ValueExpressionFactory());
		factoryMap.put(Operator.gt, new ValueExpressionFactory());
		factoryMap.put(Operator.ge, new ValueExpressionFactory());
		factoryMap.put(Operator.lt, new ValueExpressionFactory());
		factoryMap.put(Operator.le, new ValueExpressionFactory());

		factoryMap.put(Operator.like, new LikeExpressionFactory());
		factoryMap.put(Operator.likeLeft, new LikeLeftExpressionFactory());
		factoryMap.put(Operator.likeRight, new LikeRightExpressionFactory());

		factoryMap.put(Operator.in, new ListExpressionFactory());
		factoryMap.put(Operator.notIn, new ListExpressionFactory());
	}

	public static Expression buildExpression(Condition annotation, String columnName, Object value) {
	    if(annotation == null) {
	        throw new NullPointerException("Condition不能为null");
	    }
	    if(annotation.ignore()) {
	        return null;
	    }
		Operator operator = annotation.operator();
		if (operator == Operator.nil) {
			if (ClassUtil.isArrayOrCollection(value)) {
				operator = Operator.in;
			} else {
				operator = Operator.eq;
			}
		}
		ExpressionFactory expressionFactory = factoryMap.get(operator);

		String column = annotation.column();
		if ("".equals(column)) {
			column = columnName;
		}

		return expressionFactory.buildExpression(annotation.joint(), column, operator, value);
	}
	
}

package com.gitee.fastmybatis.core.query.expression.builder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.util.Assert;

import com.gitee.fastmybatis.core.ext.code.util.FieldUtil;
import com.gitee.fastmybatis.core.query.annotation.Condition;
import com.gitee.fastmybatis.core.query.expression.Expression;
import com.gitee.fastmybatis.core.query.expression.Expressions;

/**
 * 条件生成
 * 
 * @author tanghc
 *
 */
public class ConditionBuilder {
	private static final String PREFIX_GET = "get";

	private static ConditionBuilder underlineFieldBuilder = new ConditionBuilder(true);
	private static ConditionBuilder camelFieldBuilder = new ConditionBuilder(false);

	private boolean camel2underline = Boolean.TRUE;

	public ConditionBuilder() {
		super();
	}

	public ConditionBuilder(boolean camel2underline) {
		super();
		this.camel2underline = camel2underline;
	}

	public static ConditionBuilder getUnderlineFieldBuilder() {
		return underlineFieldBuilder;
	}

	public static ConditionBuilder getCamelFieldBuilder() {
		return camelFieldBuilder;
	}

	/**
	 * 获取条件表达式
	 * 
	 * @param obj
	 * @return 返回表达式结合
	 */
	public List<Expression> buildExpressions(Object obj) {
	    Assert.notNull(obj, "buildExpressions(Object obj) obj can't be null.");
		List<Expression> expList = new ArrayList<Expression>();
		Method[] methods = obj.getClass().getDeclaredMethods();
		try {
			for (Method method : methods) {
				if (couldBuildExpression(method)) {
					Object value = method.invoke(obj, new Object[] {});
					if (value == null) {
						continue;
					}
					Expression expression = buildExpression(method, value);
					if (expression != null) {
						expList.add(expression);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return expList;
	}

	private Expression buildExpression(Method method, Object value) {
		Condition annotation = method.getAnnotation(Condition.class);
		String columnName = this.buildColumnName(method);
		Expression expression = null;

		if (annotation == null) {
			if (value.getClass().isArray()) {
				expression = Expressions.in(columnName, (Object[]) value);
			} else if (value instanceof Collection) {
				expression = Expressions.in(columnName, (Collection<?>) value);
			} else {
				expression = Expressions.eq(columnName, value);
			}
		} else {
			expression = ExpressionBuilder.buildExpression(annotation, columnName, value);
		}

		return expression;
	}

	/** 返回数据库字段名 */
	private String buildColumnName(Method method) {
		String getMethodName = method.getName();
		String columnName = getMethodName.substring(3);
		columnName = FieldUtil.lowerFirstLetter(columnName);
		if (camel2underline) {
			return FieldUtil.camelToUnderline(columnName);
		} else {
			return columnName;
		}
	}

	/** 能否构建表达式 */
	private static boolean couldBuildExpression(Method method) {
		return method.getReturnType() != Void.TYPE && method.getName().startsWith(PREFIX_GET);
	}

}

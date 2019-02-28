package com.gitee.fastmybatis.core.query.expression.builder;

import com.gitee.fastmybatis.core.query.Operator;
import com.gitee.fastmybatis.core.query.annotation.Condition;
import com.gitee.fastmybatis.core.query.expression.Expression;
import com.gitee.fastmybatis.core.query.expression.Expressions;
import com.gitee.fastmybatis.core.query.expression.builder.factory.ExpressionFactory;
import com.gitee.fastmybatis.core.util.ClassUtil;
import org.apache.commons.lang.ArrayUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 负责构建条件表达式
 * 
 * @author tanghc
 */
public class ExpressionBuilder {
    private static Map<Operator, ExpressionFactory> factoryMap = new HashMap<>();
    static {
        factoryMap.put(Operator.eq, Expressions.VALUE_EXPRESSION_FACTORY);
        factoryMap.put(Operator.notEq, Expressions.VALUE_EXPRESSION_FACTORY);
        factoryMap.put(Operator.gt, Expressions.VALUE_EXPRESSION_FACTORY);
        factoryMap.put(Operator.ge, Expressions.VALUE_EXPRESSION_FACTORY);
        factoryMap.put(Operator.lt, Expressions.VALUE_EXPRESSION_FACTORY);
        factoryMap.put(Operator.le, Expressions.VALUE_EXPRESSION_FACTORY);

        factoryMap.put(Operator.like, Expressions.LIKE_EXPRESSION_FACTORY);
        factoryMap.put(Operator.likeLeft, Expressions.LIKE_LEFT_EXPRESSION_FACTORY);
        factoryMap.put(Operator.likeRight, Expressions.LIKE_RIGHT_EXPRESSION_FACTORY);

        factoryMap.put(Operator.in, Expressions.LIST_EXPRESSION_FACTORY);
        factoryMap.put(Operator.notIn, Expressions.LIST_EXPRESSION_FACTORY);
    }

    public static Expression buildExpression(Condition annotation, String columnName, Object value) {
        if (annotation == null) {
            throw new NullPointerException("Condition不能为null");
        }
        if (annotation.ignore()) {
            return null;
        }
        String[] ignoreValue = annotation.ignoreValue();
        if (ArrayUtils.contains(ignoreValue, String.valueOf(value))) {
            return null;
        }
        // 是否忽略空字符串
 		if (annotation.ignoreEmptyString() && "".equals(value)) {
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
        if (column == null || "".equals(column.trim())) {
            column = columnName;
        }

        Expression expression = expressionFactory.buildExpression(annotation.joint(), column, operator, value);
        expression.setIndex(annotation.index());

        return expression;
    }

}

package com.gitee.fastmybatis.core.query.expression;

import java.util.List;

/**
 * 查询条件支持
 * 
 * @author tanghc
 */
public interface Expressional {

    /**
     * 添加表达式
     * 
     * @param expression
     *            表达式对象
     * @return
     */
    Expressional addExpression(Expression expression);

    /**
     * 返回单值表达式
     * 
     * @return 返回单值表达式
     */
    List<ExpressionValueable> getValueExpressions();

    /**
     * 返回连接表达式列表
     * 
     * @return 返回连接表达式列表
     */
    List<ExpressionJoinable> getJoinExpressions();

    /**
     * 返回集合表达式列表
     * 
     * @return 返回集合表达式列表
     */
    List<ExpressionListable> getListExpressions();

    /**
     * 返回自定义sql表达式列表
     * 
     * @return 返回自定义sql表达式列表
     */
    List<ExpressionSqlable> getSqlExpressions();
}

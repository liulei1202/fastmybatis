package com.gitee.fastmybatis.core.query.expression;

/**
 * @author tanghc
 */
public interface ExpressionJoinable extends Expression {
    /**
     * 返回连接sql
     * 
     * @return 返回连接sql
     */
    String getJoinSql();
}

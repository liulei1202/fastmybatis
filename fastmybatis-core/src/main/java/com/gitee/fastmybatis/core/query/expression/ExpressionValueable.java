package com.gitee.fastmybatis.core.query.expression;

/**
 * @author tanghc
 */
public interface ExpressionValueable extends Expression {
    /**
     * 返回连接符
     * 
     * @return 返回连接符
     */
    String getJoint();

    /**
     * 返回数据库字段名
     * 
     * @return 返回数据库字段名
     */
    String getColumn();

    /**
     * 返回操作符
     * 
     * @return 返回操作符
     */
    String getEqual();

    /**
     * 返回值
     * 
     * @return 返回值
     */
    Object getValue();
}

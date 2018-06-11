package com.gitee.fastmybatis.core.query.expression;

import java.util.Collection;

/**
 * @author tanghc
 */
public interface ExpressionListable extends Expression {
    /**
     * 返回连接符，and , or
     * 
     * @return 返回连接符，and , or
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
    Collection<?> getValue();

}

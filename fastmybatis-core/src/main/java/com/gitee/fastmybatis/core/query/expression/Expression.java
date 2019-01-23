package com.gitee.fastmybatis.core.query.expression;

/**
 * @author tanghc
 */
public interface Expression {
    /**
     * 设置表达式index。默认为0<br/>
     *
     * 该值决定WHERE后面表达式顺序，值小的靠左。
     * @param index index值
     */
    void setIndex(int index);

    /**
     * 决定WHERE后面表达式顺序，值小的靠左。
     * @return 返回index值
     */
    int index();
}

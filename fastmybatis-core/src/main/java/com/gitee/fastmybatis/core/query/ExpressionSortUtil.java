package com.gitee.fastmybatis.core.query;

import com.gitee.fastmybatis.core.query.expression.Expression;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author tanghc
 */
public class ExpressionSortUtil {

    private ExpressionSortUtil() {
    }

    /**
     * 给条件排序
     * @param expressionList 条件列表
     * @param <T> 表达式
     * @return 返回排序后的列表
     */
    public static <T extends Expression> List<T> sort(List<T> expressionList) {
        if (expressionList == null) {
            return null;
        }

        Collections.sort(expressionList, new Comparator<Expression>() {
            @Override
            public int compare(Expression o1, Expression o2) {
                return Integer.compare(o1.index(), o2.index());
            }
        });
        return expressionList;
    }

}

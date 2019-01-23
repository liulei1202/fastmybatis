package com.gitee.fastmybatis.core.query;

import com.gitee.fastmybatis.core.query.expression.Expression;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author tanghc
 */
public class ExpressionSortUtil {
    public static <T extends Expression> List<T> sort(List<T> expressionList) {
        Collections.sort(expressionList, new Comparator<Expression>() {
            @Override
            public int compare(Expression o1, Expression o2) {
                return Integer.compare(o1.index(), o2.index());
            }
        });
        return expressionList;
    }
}

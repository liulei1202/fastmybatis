package com.gitee.fastmybatis.core.query;

import java.util.List;
import java.util.Map;

import com.gitee.fastmybatis.core.query.expression.Expressional;

/**
 * 默认实现类:component.dao.query.Query
 * 
 * @author tanghc
 *
 */
public interface Queryable extends Sortable, Expressional, Pageable {
    /**
     * 返回自定义参数
     * 
     * @return 返回自定义参数
     */
    Map<String, Object> getParam();

    /**
     * 返回列名
     * 
     * @return 返回列名
     */
    List<String> getColumns();

    /**
     * 返回其它表的列名
     * 
     * @return 返回其它表的列名
     */
    List<String> getOtherTableColumns();

}

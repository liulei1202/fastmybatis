package com.gitee.fastmybatis.core.query;

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

}

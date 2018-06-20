package com.gitee.fastmybatis.core.query;

import java.util.Map;

import com.gitee.fastmybatis.core.query.expression.Expressional;

/**
 * @author tanghc
 */
public interface Queryable extends Sortable, Expressional, Pageable {
    /**
     * 返回自定义参数，在xml中使用<code>#{参数名}</code>获取值
     * 
     * @return 返回自定义参数
     */
    Map<String, Object> getParam();

}

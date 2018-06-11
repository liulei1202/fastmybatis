package com.gitee.fastmybatis.core;

import java.io.Serializable;
import java.util.List;

/**
 * @author tanghc
 */
public interface PageResult<Entity> extends Serializable {
    /**
     * 设置结果集
     * 
     * @param list
     *            结果集
     */
    void setList(List<Entity> list);

    /**
     * 设置记录总数
     * 
     * @param total
     *            总记录数
     */
    void setTotal(long total);

    /**
     * 设置第一条记录起始位置
     * 
     * @param start
     *            起始位置
     */
    void setStart(int start);

    /**
     * 设置当前页索引
     * 
     * @param pageIndex
     *            当前页索引
     */
    void setPageIndex(int pageIndex);

    /**
     * 设置每页记录数
     * 
     * @param pageSize
     *            每页记录数
     */
    void setPageSize(int pageSize);

    /**
     * 设置总页数
     * 
     * @param pageCount
     *            总页数
     */
    void setPageCount(int pageCount);
}

package com.gitee.fastmybatis.core.query;

/**
 * 分页支持
 * 
 * @author tanghc
 */
public interface Pageable {
    /**
     * 返回其实位置
     * @return 返回其实位置
     */
    int getStart();

    /**
     * 返回偏移量
     * @return 返回偏移量
     */
    int getLimit();

    /**
     * 是否查询全部
     * @return 是否查询全部
     */
    boolean getIsQueryAll();

    /**
     * 返回总记录数
     * @return 返回总记录数
     */
    int getTotal();

    /**
     * 是否设置总记录数
     * @return 返回是否设置总记录数
     */
    boolean getIsSetTotal();
}

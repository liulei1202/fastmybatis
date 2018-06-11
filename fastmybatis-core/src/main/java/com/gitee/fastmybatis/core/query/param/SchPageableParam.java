package com.gitee.fastmybatis.core.query.param;

/**
 * 分页排序查询参数
 * 
 * @author tanghc
 */
public interface SchPageableParam extends SchParam {
    /**
     * 返回第一条记录的索引值
     * 
     * @return 返回第一条记录的索引值
     */
    int getStart();

    /**
     * 返回第一条记录的索引值
     * 
     * @return 返回第一条记录的索引值
     */
    int getLimit();
}

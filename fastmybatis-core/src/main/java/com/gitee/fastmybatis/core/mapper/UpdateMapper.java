package com.gitee.fastmybatis.core.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gitee.fastmybatis.core.query.Query;

/**
 * 具备更新功能的Mapper
 * 
 * @param <E> 实体类
 * @author tanghc
 */
public interface UpdateMapper<E> extends Mapper<E> {
    /**
     * 更新，更新所有字段
     * 
     * @param entity 实体类
     * @return 受影响行数
     */
    int update(E entity);

    /**
     * 更新，忽略null字段
     * 
     * @param entity 实体类
     * @return 受影响行数
     */
    int updateIgnoreNull(E entity);

    /**
     * 根据条件批量更新
     * 
     * @param entity
     *            待更新的数据，可以是实体类，也可以是Map&lt;String,Object&gt;
     * @param query
     *            更新条件
     * @return 受影响行数
     */
    int updateByQuery(@Param("entity") Object entity, @Param("query") Query query);
    
    /**
     * 根据条件更新，map中的数据转化成update语句set部分，key为数据库字段名
     * @param map 待更新的数据，key为数据库字段名
     * @param query 更新条件
     * @return 受影响行数
     */
    int updateByMap(@Param("entity") Map<String, Object> map, @Param("query") Query query);
}

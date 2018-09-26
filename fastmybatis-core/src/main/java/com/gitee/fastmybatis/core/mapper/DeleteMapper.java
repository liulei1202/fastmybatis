package com.gitee.fastmybatis.core.mapper;

import org.apache.ibatis.annotations.Param;

import com.gitee.fastmybatis.core.query.Query;

/**
 * 具备删除功能的Mapper
 * 
 * @param <E> 实体类
 * @param <I> 主键类型，如Long
 * @author tanghc
 */
public interface DeleteMapper<E, I> extends Mapper<E> {
    /**
     * 删除
     * 
     * @param entity
     *            实体类
     * @return 受影响行数
     */
    int delete(E entity);

    /**
     * 根据id删除
     * 
     * @param id
     *            主键id值
     * @return 受影响行数
     */
    int deleteById(I id);

    /**
     * 根据条件删除
     * 
     * @param query
     *            查询对象
     * @return 受影响行数
     */
    int deleteByQuery(@Param("query") Query query);
}

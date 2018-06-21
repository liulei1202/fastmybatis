package com.gitee.fastmybatis.core.mapper;

import org.apache.ibatis.annotations.Param;

import com.gitee.fastmybatis.core.query.Query;

/**
 * 具备删除功能的Mapper
 * 
 * @author tanghc
 */
public interface DeleteMapper<Entity, ID> extends Mapper<Entity> {
    /**
     * 删除
     * 
     * @param entity
     *            实体类
     * @return 受影响行数
     */
    int delete(Entity entity);

    /**
     * 根据id删除
     * 
     * @param id
     *            主键id值
     * @return 受影响行数
     */
    int deleteById(ID id);

    /**
     * 根据条件删除
     * 
     * @param query
     *            查询对象
     * @return 受影响行数
     */
    int deleteByQuery(@Param("query") Query query);
}

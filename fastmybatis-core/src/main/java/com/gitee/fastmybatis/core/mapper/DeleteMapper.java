package com.gitee.fastmybatis.core.mapper;

import com.gitee.fastmybatis.core.query.Query;

/**
 * @author tanghc
 */
public interface DeleteMapper<Entity, ID> extends Mapper<Entity> {
    /**
     * 删除
     * 
     * @param entity
     * @return 受到影响的行数
     */
    int delete(Entity entity);

    /**
     * 根据id删除
     * 
     * @param id
     *            主键id
     * @return 受到影响的行数
     */
    int deleteById(ID id);

    /**
     * 根据条件删除
     * 
     * @param query
     * @return 受到影响的行数
     */
    int deleteByQuery(Query query);
}

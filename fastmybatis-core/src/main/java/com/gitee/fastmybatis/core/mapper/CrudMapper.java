package com.gitee.fastmybatis.core.mapper;

/**
 * 具备CRUD的mapper
 * 
 * @param <Entity>
 *            实体类
 * @param <ID>
 *            主键类型
 * @author tanghc
 */
public interface CrudMapper<Entity, ID> extends SchMapper<Entity, ID>, EditMapper<Entity, ID> {

}

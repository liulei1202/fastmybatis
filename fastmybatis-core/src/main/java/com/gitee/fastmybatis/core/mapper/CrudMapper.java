package com.gitee.fastmybatis.core.mapper;

/**
 * 具备CRUD的mapper
 * 
 * @param <E> 实体类
 * @param <I> 主键类型，如Long
 * @author tanghc
 */
public interface CrudMapper<E, I> extends SchMapper<E, I>, EditMapper<E, I> {

}

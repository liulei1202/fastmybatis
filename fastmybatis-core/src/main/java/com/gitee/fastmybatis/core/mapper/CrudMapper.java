package com.gitee.fastmybatis.core.mapper;

/**
 * 具备CRUD的mapper
 *
 * @param <E> 实体类，如：Student
 * @param <I> 主键类型，如：Long，Integer
 * @author tanghc
 */
public interface CrudMapper<E, I> extends SchMapper<E, I>, EditMapper<E, I> {

}

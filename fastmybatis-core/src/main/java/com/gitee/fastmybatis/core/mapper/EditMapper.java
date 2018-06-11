package com.gitee.fastmybatis.core.mapper;

/**
 * @author tanghc
 */
public interface EditMapper<Entity, ID> extends SaveMapper<Entity>, UpdateMapper<Entity>, DeleteMapper<Entity, ID> {

}

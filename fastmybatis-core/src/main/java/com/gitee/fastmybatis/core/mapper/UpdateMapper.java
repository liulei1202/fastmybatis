package com.gitee.fastmybatis.core.mapper;

/**
 * 具备更新功能的Mapper
 * @author tanghc
 */
public interface UpdateMapper<Entity> extends Mapper<Entity> {
	/**
	 * 修改,修改所有字段
	 * 
	 * @param entity
	 * @return 受到影响的行数
	 */
	int update(Entity entity);

	/**
	 * 根据主键更新不为null的字段
	 * 
	 * @param entity
	 * @return 受到影响的行数
	 */
	int updateIgnoreNull(Entity entity);
}

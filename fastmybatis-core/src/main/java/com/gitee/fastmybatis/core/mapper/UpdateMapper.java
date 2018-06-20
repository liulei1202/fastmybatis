package com.gitee.fastmybatis.core.mapper;

import org.apache.ibatis.annotations.Param;

import com.gitee.fastmybatis.core.query.Query;

/**
 * 具备更新功能的Mapper
 * @author tanghc
 */
public interface UpdateMapper<Entity> extends Mapper<Entity> {
	/**
	 * 修改,修改所有字段
	 * 
	 * @param entity
	 * @return 受影响行数
	 */
	int update(Entity entity);

	/**
	 * 根据主键更新不为null的字段
	 * 
	 * @param entity
	 * @return 受影响行数
	 */
	int updateIgnoreNull(Entity entity);
	
	/**
     * 根据条件批量更新
     * 
     * @param entity 待更新的数据，可以是实体类，也可以是Map{@literal<}String,Object{@literal>}
     * @param query 更新条件
     * @return 受影响行数
     */
    int updateByQuery(@Param("entity") Object entity, @Param("query") Query query);
}

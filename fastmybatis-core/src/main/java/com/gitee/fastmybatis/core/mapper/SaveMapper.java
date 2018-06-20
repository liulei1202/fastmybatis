package com.gitee.fastmybatis.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 具备保存功能的Mapper
 * @author tanghc
 */
public interface SaveMapper<Entity> extends Mapper<Entity> {
	/**
	 * 新增,新增所有字段
	 * 
	 * @param entity
	 * @return 受影响行数
	 */
	int save(Entity entity);
	
	/**
	 * 新增（忽略null数据）
	 * @param entity
	 * @return 受影响行数
	 */
	int saveIgnoreNull(Entity entity);
	
	/**
	 * 批量添加,只支持mysql,sqlserver2008及以上数据库.<br>
	 * <strong>若要兼容其它版本数据库,请使用saveMulti()方法</strong>
	 * @param entitys
	 * @return 受影响行数
	 */
	int saveBatch(@Param("entitys")List<Entity> entitys);
	
	/**
	 * 批量添加,兼容更多的数据库版本.<br>
	 * 此方式采用union all的方式批量insert,如果是mysql或sqlserver2008及以上推荐saveBatch()方法.
	 * @param entitys
	 * @return 受影响行数
	 */
	int saveMulti(@Param("entitys")List<Entity> entitys);
	
}

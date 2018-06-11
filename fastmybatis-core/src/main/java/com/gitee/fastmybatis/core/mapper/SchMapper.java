package com.gitee.fastmybatis.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gitee.fastmybatis.core.query.Query;

/**
 * 具备查询功能的Mapper
 * @author tanghc
 */
public interface SchMapper<Entity, ID> extends Mapper<Entity> {
	
	/**
	 * 默认的resultMap
	 */
	String BASE_RESULT_MAP = "baseResultMap";

	/**
	 * 根据对象查询,可以传主键值,也可以传整个对象
	 * 
	 * @param id
	 * @return
	 */
	Entity getById(ID id);
	
	/**
	 * 根据条件查找单条记录
	 * @param query 查询条件
	 * @return 返回实体对象，没有返回null
	 */
	Entity getByQuery(Query query);
	
	/**
	 * 根据字段查询一条记录
	 * @param column 数据库字段名
	 * @param value 字段值
	 * @return 返回实体对象，没有返回null
	 */
	Entity getByProperty(@Param("column")String column,@Param("value")Object value);
	
	/**
	 * 根据字段查询集合
	 * @param column 数据库字段名
	 * @param value 字段值
	 * @return 返回实体对象集合，没有返回空集合
	 */
	List<Entity> listByProperty(@Param("column")String column,@Param("value")Object value);
	
	/**
	 * 条件查询
	 * 
	 * @param query 查询条件
	 * @return 返回实体对象集合，没有返回空集合
	 */
	List<Entity> list(Query query);
	
	/**
	 * 查询总记录数
	 * 
	 * @param query 查询条件
	 * @return 返回总记录数
	 */
	long countTotal(Query query);	

}

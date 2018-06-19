package com.gitee.fastmybatis.core.mapper;

import java.util.List;
import java.util.Map;

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
	 * @return 返回实体对象，没有返回null
	 */
	Entity getById(ID id);
	
	/**
	 * 根据条件查找单条记录
	 * @param query 查询条件
	 * @return 返回实体对象，没有返回null
	 */
	Entity getByQuery(@Param("query")Query query);
	
	/**
	 * 根据字段查询一条记录
	 * @param column 数据库字段名
	 * @param value 字段值
	 * @return 返回实体对象，没有返回null
	 */
	Entity getByColumn(@Param("column")String column,@Param("value")Object value);
	
	/**
     * 查询总记录数
     * 
     * @param query 查询条件
     * @return 返回总记录数
     */
    long getCount(@Param("query")Query query);  
	
	/**
	 * 根据字段查询集合
	 * @param column 数据库字段名
	 * @param value 字段值
	 * @return 返回实体对象集合，没有返回空集合
	 */
	List<Entity> listByColumn(@Param("column")String column,@Param("value")Object value);
	
	/**
	 * 条件查询
	 * 
	 * @param query 查询条件
	 * @return 返回实体对象集合，没有返回空集合
	 */
	List<Entity> list(@Param("query")Query query);
	
	/**
	 * 查询指定字段结果
	 * @param columns 返回的字段
	 * @param query 查询条件
	 * @return 返回结果集
	 */
	List<Map<String, Object>> listMap(@Param("columns")List<String> columns, @Param("query")Query query);
	
}

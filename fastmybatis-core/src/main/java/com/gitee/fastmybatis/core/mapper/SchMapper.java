package com.gitee.fastmybatis.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gitee.fastmybatis.core.query.Query;

/**
 * 具备查询功能的Mapper
 * 
 * @param <E> 实体类，如：Student
 * @param <I> 主键类型，如：Long，Integer
 * @author tanghc
 */
public interface SchMapper<E, I> extends Mapper<E> {

    /**
     * 内置resultMap名称
     */
    String BASE_RESULT_MAP = "baseResultMap";

    /**
     * 根据主键查询
     * 
     * @param id
     *            主键值
     * @return 返回实体对象，没有返回null
     */
    E getById(I id);

    /**
     * 根据条件查找单条记录
     * 
     * @param query
     *            查询条件
     * @return 返回实体对象，没有返回null
     */
    E getByQuery(@Param("query") Query query);

    /**
     * 根据字段查询一条记录
     * 
     * @param column
     *            数据库字段名
     * @param value
     *            字段值
     * @return 返回实体对象，没有返回null
     */
    E getByColumn(@Param("column") String column, @Param("value") Object value);

    /**
     * 查询总记录数
     * 
     * @param query
     *            查询条件
     * @return 返回总记录数
     */
    long getCount(@Param("query") Query query);

    /**
     * 根据字段查询结果集
     * 
     * @param column
     *            数据库字段名
     * @param value
     *            字段值
     * @return 返回实体对象集合，没有返回空集合
     */
    List<E> listByColumn(@Param("column") String column, @Param("value") Object value);

    /**
     * 查询结果集
     * 
     * @param query
     *            查询条件
     * @return 返回实体对象集合，没有返回空集合
     */
    List<E> list(@Param("query") Query query);

    /**
     * 查询指定字段结果，Map里面key对应字段名，value对应值<br>
     * 使用ClassUtil.mapListToObjList(listMap, Class pojoClass)方法将List&lt;Map&gt;转换成List&lt;Entity&gt;
     * @param columns
     *            返回的字段
     * @param query
     *            查询条件
     * @return 返回结果集,没有返回空list
     * @see com.gitee.fastmybatis.core.util.ClassUtil#mapListToObjList(List, Class) ClassUtil.mapListToObjList(List, Class)
     */
    List<Map<String, Object>> listMap(@Param("columns") List<String> columns, @Param("query") Query query);

}

package com.gitee.fastmybatis.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 具备保存功能的Mapper
 * 
 * @param <E>实体类
 * @author tanghc
 */
public interface SaveMapper<E> extends Mapper<E> {
    /**
     * 保存，保存所有字段
     * 
     * @param entity
     * @return 受影响行数
     */
    int save(E entity);

    /**
     * 保存，忽略null字段
     * 
     * @param entity
     * @return 受影响行数
     */
    int saveIgnoreNull(E entity);

    /**
     * 批量保存,只支持mysql,sqlserver2008及以上数据库.<br>
     * <strong>若要兼容其它版本数据库,请使用saveMulti()方法</strong>
     * 
     * @param entitys
     * @return 受影响行数
     * @see #saveMulti(List) 批量兼容版本
     */
    int saveBatch(@Param("entitys") List<E> entitys);

    /**
     * 批量保存,兼容更多的数据库版本.<br>
     * 此方式采用union all的方式批量insert,如果是mysql或sqlserver2008及以上推荐saveBatch()方法.
     * 
     * @param entitys
     * @return 受影响行数
     */
    int saveMulti(@Param("entitys") List<E> entitys);

    /**
     * 批量保存,兼容更多的数据库版本,忽略重复行.<br>
     * 此方式采用union的方式批量insert.
     *
     * @param entitys
     * @return 受影响行数
     */
    int saveMultiSet(@Param("entitys") List<E> entitys);
}

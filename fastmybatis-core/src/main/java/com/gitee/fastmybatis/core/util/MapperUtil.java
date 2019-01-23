package com.gitee.fastmybatis.core.util;

import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.PageResult;
import com.gitee.fastmybatis.core.exception.QueryException;
import com.gitee.fastmybatis.core.mapper.SchMapper;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.param.BaseParam;
import com.gitee.fastmybatis.core.support.PageEasyui;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 查询工具
 * 
 * @author tanghc
 *
 */
public class MapperUtil {
    private MapperUtil() {
	}


    /**
     * 为easyui表格(datagrid)提供的查询
     *
     * @param mapper mapper
     * @param query  查询条件
     * @param clazz  返回VO的类型
     * @param <E>    结果集
     * @param <T>    VO
     * @return 返回查询结果，将此对象转换成json，可被datagrid识别
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <E, T> PageEasyui<T> queryForEasyuiDatagrid(SchMapper<E, ?> mapper, Query query, Class<T> clazz) {
        PageEasyui pageInfo = queryForEasyuiDatagrid(mapper, query);
        List list = pageInfo.getRows();
        if (CollectionUtils.isNotEmpty(list)) {
            List<Object> newList = new ArrayList<>(list.size());
            try {
                for (Object element : list) {
                    if (clazz == element.getClass()) {
                        newList.add(element);
                    } else {
                        T t = clazz.newInstance();
                        BeanUtils.copyProperties(element, t);
                        newList.add(t);
                    }
                }
                pageInfo.setList(newList);
            } catch (Exception e) {
                throw new QueryException(e);
            }
        } else {
        	pageInfo.setList(Collections.<T>emptyList());
        }
        return (PageEasyui<T>)pageInfo;
    }

    /**
     * 为easyui表格(datagrid)提供的查询
     *
     * @param mapper mapper
     * @param query  查询对象
     * @param <E>    结果集对象
     * @return 返回查询结果，将此对象转换成json，可被datagrid识别
     */
    @SuppressWarnings("unchecked")
    public static <E> PageEasyui<E> queryForEasyuiDatagrid(SchMapper<E, ?> mapper, Query query) {
        return query(mapper, query, PageEasyui.class);
    }

	/**
     * 分页数算法:页数 = (总记录数 + 每页记录数 - 1) / 每页记录数
     * 
     * @param total 总记录数
     * @param pageSize 每页记录数
     * @return 返回页数
     */
    private static int calcPageCount(long total, int pageSize) {
        return (int) (pageSize == 0 ? 1 : (total + pageSize - 1) / pageSize);
    }

    /**
     * 分页查询
     * 
     * @param <Entity> 实体类
     * @param mapper
     *            查询mapper
     * @param bean
     *            查询bean
     * @return 返回PageInfo
     */
    @SuppressWarnings("unchecked")
    public static <Entity> PageInfo<Entity> query(SchMapper<Entity, ?> mapper, Object bean) {
        return query(mapper, Query.build(bean), PageInfo.class);
    }

    /**
     * 分页查询
     * 
     * @param <Entity> 实体类
     * @param mapper
     *            查询mapper
     * @param searchParam
     *            查询pojo
     * @return 返回PageInfo，里面存放结果以及分页信息
     */
    @SuppressWarnings("unchecked")
    public static <Entity> PageInfo<Entity> query(SchMapper<Entity, ?> mapper, BaseParam searchParam) {
        return query(mapper, Query.build(searchParam), PageInfo.class);
    }

    /**
     * 分页查询
     * 
     * @param <Entity> 实体类
     * @param mapper
     *            查询mapper
     * @param query
     *            查询条件
     * @return 返回PageInfo，里面存放结果以及分页信息
     */
    @SuppressWarnings("unchecked")
    public static <Entity> PageInfo<Entity> query(SchMapper<Entity, ?> mapper, Query query) {
        return query(mapper, query, PageInfo.class);
    }

    /**
     * 分页查询
     * 
     * @param <Entity> 实体类
     * @param <T> 返回结果类
     * 
     * @param mapper
     *            查询mapper
     * @param query
     *            查询条件
     * @param pageResultClass
     *            结果类
     * @return 返回结果类
     */
    public static <Entity, T extends PageResult<Entity>> T query(SchMapper<Entity, ?> mapper, Query query,
            Class<T> pageResultClass) {
        T result = null;
        try {
            result = pageResultClass.newInstance();
        } catch (Exception e) {
            throw new QueryException(e);
        }

        try {
            // 总页数
            int pageCount = 0;
            // 总条数
            long total = 0;
            // 结果集
            List<Entity> list = Collections.emptyList();

            // 如果是查询全部则直接返回结果集条数
            // 如果是分页查询则还需要带入条件执行一下sql
            if (query.getIsQueryAll()) {
                list = mapper.list(query);
                total = list.size();
                if (total > 0) {
                    pageCount = 1;
                }
            } else {
                if (query.getIsSetTotal()){
                    //如果设置了total总记录数，直接获取该total
                    total = query.getTotal();
                }else {
                    //如果没有设置total，先去count执行一下sql
                    total = mapper.getCount(query);
                }
                // 如果有数据
                if (total > 0) {
                    list = mapper.list(query);

                    int start = query.getStart();
                    // 每页记录数
                    int pageSize = query.getLimit();
                    // 当前第几页
                    int pageIndex = (start / pageSize) + 1;

                    result.setStart(start);
                    result.setPageIndex(pageIndex);
                    result.setPageSize(pageSize);

                    pageCount = calcPageCount(total, pageSize);
                }
            }

            result.setList(list);
            result.setTotal(total);
            result.setPageCount(pageCount);
        } catch (Exception e) {
            throw new QueryException(e);
        }

        return result;
    }
    
}

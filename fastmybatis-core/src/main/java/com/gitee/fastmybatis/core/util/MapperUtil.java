package com.gitee.fastmybatis.core.util;

import java.util.Collections;
import java.util.List;

import com.gitee.fastmybatis.core.PageInfo;
import com.gitee.fastmybatis.core.PageResult;
import com.gitee.fastmybatis.core.mapper.SchMapper;
import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.param.BaseParam;

/**
 * 查询工具
 * 
 * @author tanghc
 *
 */
public class MapperUtil {
    /**
     * 分页数算法:页数 = (总记录数 + 每页记录数 - 1) / 每页记录数
     * 
     * @param total
     * @param pageSize
     * @return
     */
    private static int calcPageCount(long total, int pageSize) {
        return (int) (pageSize == 0 ? 1 : (total + pageSize - 1) / pageSize);
    }

    /**
     * 分页查询
     * 
     * @param schDao
     *            查询dao
     * @param bean
     *            查询bean
     * @return 返回PageInfo
     */
    @SuppressWarnings("unchecked")
    public static <Entity> PageInfo<Entity> query(SchMapper<Entity, ?> schDao, Object bean) {
        return query(schDao, Query.build(bean), PageInfo.class);
    }

    /**
     * 分页查询
     * 
     * @param schDao
     *            查询dao
     * @param searchParam
     *            查询pojo
     * @return 返回PageInfo
     */
    @SuppressWarnings("unchecked")
    public static <Entity> PageInfo<Entity> query(SchMapper<Entity, ?> schDao, BaseParam searchParam) {
        return query(schDao, Query.build(searchParam), PageInfo.class);
    }

    /**
     * 分页查询
     * 
     * @param schDao
     *            查询dao
     * @param query
     *            查询条件
     * @return 返回PageInfo
     */
    @SuppressWarnings("unchecked")
    public static <Entity> PageInfo<Entity> query(SchMapper<Entity, ?> schDao, Query query) {
        return query(schDao, query, PageInfo.class);
    }

    /**
     * 分页查询
     * 
     * @param schDao
     *            查询dao
     * @param query
     *            查询条件
     * @param pageResultClass
     *            结果类
     * @return 返回结果类
     */
    public static <Entity, T extends PageResult<Entity>> T query(SchMapper<Entity, ?> schDao, Query query,
            Class<T> pageResultClass) {
        T result = null;
        try {
            result = pageResultClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            // 总条数
            long total = 0;
            // 结果集
            List<Entity> list = Collections.emptyList();

            // 如果是查询全部则直接返回结果集条数
            // 如果是分页查询则还需要带入条件执行一下sql
            if (query.getIsQueryAll()) {
                list = schDao.list(query);
                total = list.size();
            } else {
                total = schDao.countTotal(query);
                // 如果有数据
                if (total > 0) {
                    list = schDao.list(query);

                    int start = query.getStart();
                    // 每页记录数
                    int pageSize = query.getLimit();
                    // 当前第几页
                    int pageIndex = (start / pageSize) + 1;

                    result.setStart(start);
                    result.setPageIndex(pageIndex);
                    result.setPageSize(pageSize);

                    int pageCount = calcPageCount(total, pageSize);
                    result.setPageCount(pageCount);
                }
            }

            result.setList(list);
            result.setTotal(total);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}

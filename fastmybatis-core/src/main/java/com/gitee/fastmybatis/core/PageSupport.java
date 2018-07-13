package com.gitee.fastmybatis.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gitee.fastmybatis.core.util.MyBeanUtil;

/**
 * @author tanghc
 */
public class PageSupport<Entity> implements PageResult<Entity> {
    private static final long serialVersionUID = 5931004082164727399L;
    
    private List<Entity> list;
    private long total = 0;
    private int start = 0;
    private int pageIndex = 1;
    private int pageSize = 10;
    private int pageCount = 0;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public PageResult process(EntityProcessor<Entity> processor) {
        if (processor == null) {
            return this;
        }
        PageResult pageResult = this;
        if (processor != null) {
            List<Object> jsonObjList = processEntityToJSONObject(list, processor);
            pageResult.setList(jsonObjList);
        }
        return pageResult;
    }

    /**
     * 将list中的entity对象处理成JSONObject对象 
     * @param list 
     * @param processor
     * @return 返回新的结果集
     */
    public static <Entity> List<Object> processEntityToJSONObject(List<Entity> list,
            EntityProcessor<Entity> processor) {
        List<Object> jsonObjList = new ArrayList<Object>(list.size());

        for (Entity entity : list) {
            Map<String, Object> jsonObject = MyBeanUtil.pojoToMap(entity);
            processor.process(entity, jsonObject);
            jsonObjList.add(jsonObject);
        }

        return jsonObjList;
    }

    public int fatchCurrentPageIndex() {
        return pageIndex;
    }

    /**
     * 上一页
     * 
     * @return 返回上一页页码
     */
    public int fatchPrePageIndex() {
        return (pageIndex - 1 <= 0) ? 1 : pageIndex - 1;
    }

    /**
     * 下一页
     * 
     * @return 返回下一页页码
     */
    public int fatchNextPageIndex() {
        return (pageIndex + 1 > pageCount) ? pageCount : pageIndex + 1;
    }

    /**
     * 首页
     * 
     * @return 返回1
     */
    public int fatchFirstPageIndex() {
        return 1;
    }

    /**
     * 最后一页
     * 
     * @return 返回最后一页页码
     */
    public int fatchLastPageIndex() {
        return pageCount;
    }

    /**
     * 结果集
     * 
     * @return 返回结果集
     */
    public List<Entity> fatchList() {
        return list;
    }

    /**
     * 总记录数
     * 
     * @return 返回总记录数
     */
    public long fatchTotal() {
        return total;
    }

    /**
     * 当前页索引,等同于getCurrentPageIndex()
     * 
     * @return 返回当前页索引
     */
    public int fatchPageIndex() {
        return pageIndex;
    }

    /**
     * 每页显示几条记录
     * 
     * @return 返回每页大小
     */
    public int fatchPageSize() {
        return pageSize;
    }

    /**
     * 起始页索引，从0开始
     * @return 返回起始页索引
     */
    public int fatchStart() {
        return start;
    }

    /**
     * 共几页
     * 
     * @return 返回总页数
     */
    public int fatchPageCount() {
        return pageCount;
    }

    @Override
    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public void setList(List<Entity> list) {
        this.list = list;
    }

}

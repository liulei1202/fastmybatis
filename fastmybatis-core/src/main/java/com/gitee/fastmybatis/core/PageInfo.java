package com.gitee.fastmybatis.core;

import java.util.List;

/**
 * 封装查询结果
 * 
 * @param <E> 实体类
 * @author tanghc
 */
public class PageInfo<E> extends PageSupport<E> {
    private static final long serialVersionUID = 5104636317609298856L;

    /**
     * 当前页
     * @return 返回当前页页码
     */
    public int getCurrentPageIndex() {
		return this.fatchCurrentPageIndex();
	}

	/**
	 * 上一页
	 * 
	 * @return 返回上一页页码
	 */
	public int getPrePageIndex() {
		return this.fatchPrePageIndex();
	}

	/**
	 * 下一页
	 * 
	 * @return 返回下一页页码
	 */
	public int getNextPageIndex() {
		return this.fatchNextPageIndex();
	}

	/**
	 * 首页
	 * 
	 * @return 返回1
	 */
	public int getFirstPageIndex() {
		return FIRST_PAGE;
	}

	/**
	 * 最后一页
	 * 
	 * @return 返回最后一页页码
	 */
	public int getLastPageIndex() {
		return this.fatchLastPageIndex();
	}

	/**
	 * 结果集
	 * 
	 * @return 返回结果集
	 */
	public List<E> getList() {
		return this.fatchList();
	}

	/**
	 * 总记录数
	 * 
	 * @return 返回总记录数
	 */
	public long getTotal() {
		return this.fatchTotal();
	}

	/**
	 * 当前页索引,等同于getCurrentPageIndex()
	 * 
	 * @return 返回当前页索引
	 */
	public int getPageIndex() {
		return this.fatchCurrentPageIndex();
	}

	/**
	 * 每页显示几条记录
	 * 
	 * @return 返回每页大小
	 */
	public int getPageSize() {
		return this.fatchPageSize();
	}

	/**
	 * 共几页
	 * 
	 * @return 返回总页数
	 */
	public int getPageCount() {
		return this.fatchPageCount();
	}

	/**
	 * 起始页索引，从0开始，不同于pageIndex
	 * @return 返回起始页索引
	 */
	public int getStart() {
		return this.fatchStart();
	}

}

package com.gitee.fastmybatis.core;

import java.util.List;

/**
 * 查询结果
 * 
 * @author tanghc
 *
 * @param <Entity>
 */
public class PageInfo<Entity> extends PageSupport<Entity> {
    private static final long serialVersionUID = 5104636317609298856L;

    public int getCurrentPageIndex() {
		return this.fatchCurrentPageIndex();
	}

	/**
	 * 上一页
	 * 
	 * @return
	 */
	public int getPrePageIndex() {
		return this.fatchPrePageIndex();
	}

	/**
	 * 下一页
	 * 
	 * @return
	 */
	public int getNextPageIndex() {
		return this.fatchNextPageIndex();
	}

	/**
	 * 首页
	 * 
	 * @return
	 */
	public int getFirstPageIndex() {
		return 1;
	}

	/**
	 * 最后一页
	 * 
	 * @return
	 */
	public int getLastPageIndex() {
		return this.fatchLastPageIndex();
	}

	/**
	 * 结果集
	 * 
	 * @return
	 */
	public List<Entity> getList() {
		return this.fatchList();
	}

	/**
	 * 总记录数
	 * 
	 * @return
	 */
	public long getTotal() {
		return this.fatchTotal();
	}

	/**
	 * 当前页索引,等同于getCurrentPageIndex()
	 * 
	 * @return
	 */
	public int getPageIndex() {
		return this.fatchCurrentPageIndex();
	}

	/**
	 * 每页显示几条记录
	 * 
	 * @return
	 */
	public int getPageSize() {
		return this.fatchPageSize();
	}

	/**
	 * 共几页
	 * 
	 * @return
	 */
	public int getPageCount() {
		return this.fatchPageCount();
	}

	public int getStart() {
		return this.fatchStart();
	}

}

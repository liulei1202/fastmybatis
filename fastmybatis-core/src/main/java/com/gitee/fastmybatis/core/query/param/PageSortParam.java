package com.gitee.fastmybatis.core.query.param;

import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.annotation.Condition;

/**
 * 分页排序查询参数
 * @author tanghc
 *
 */
public class PageSortParam extends BaseParam implements SchPageableParam,SchSortableParam {

	/** 当前第几页 */
	private int pageIndex = 1;
	/** 每页记录数 */
	private int pageSize = 20;

	private String sort;
	private String order;
	
	@Override
	public Query toQuery() {
		return super.toQuery().addPaginationInfo(this).addSortInfo(this);
	}

	@Condition(ignore = true)
	@Override
	public String getSortname() {
		return sort;
	}

	@Condition(ignore = true)
	@Override
	public String getSortorder() {
		return order;
	}

	@Condition(ignore = true)
	@Override
	public int getStart() {
		return (int) ((this.getPageIndex() - 1) * this.getPageSize());
	}

	@Condition(ignore = true)
	@Override
	public int getLimit() {
		return this.getPageSize();
	}

	@Condition(ignore = true)
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	@Condition(ignore = true)
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Condition(ignore = true)
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Condition(ignore = true)
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Condition(ignore = true)
	@Override
	public String getDBSortname() {
		return this.sort;
	}

}

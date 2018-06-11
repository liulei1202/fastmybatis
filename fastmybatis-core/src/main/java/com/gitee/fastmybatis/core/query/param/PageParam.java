package com.gitee.fastmybatis.core.query.param;

import com.gitee.fastmybatis.core.query.Query;
import com.gitee.fastmybatis.core.query.annotation.Condition;

/**
 * 分页查询参数
 * 
 * @author tanghc
 *
 */
public class PageParam extends BaseParam implements SchPageableParam {

	/** 当前第几页 */
	private int pageIndex = 1;
	/** 每页记录数 */
	private int pageSize = 20;

	@Override
	public Query toQuery() {
		return super.toQuery().addPaginationInfo(this);
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

}

package com.gitee.fastmybatis.core.support;

import java.util.List;

import com.gitee.fastmybatis.core.PageSupport;

/**
 * 支持easyui表格的json结果<br>
 * <code>{"total":28,"rows":[{...},{...}]}</code>
 * @author tanghc
 *
 * @param <Entity>
 */
public class PageEasyui<Entity> extends PageSupport<Entity> {
    private static final long serialVersionUID = 2599057675920773433L;

    public long getTotal() {
		return this.fatchTotal();
	}
	
	public List<Entity> getRows() {
		return this.fatchList();
	}
}

package com.gitee.fastmybatis.core.support;

import java.util.List;

import com.gitee.fastmybatis.core.PageSupport;

/**
 * 支持easyui表格的json结果<br>
 * <code>{"total":28,"rows":[{...},{...}]}</code>
 * @param <E> 实体类
 * 
 * @author tanghc
 */
public class PageEasyui<E> extends PageSupport<E> {
    private static final long serialVersionUID = 2599057675920773433L;

    public long getTotal() {
		return this.fatchTotal();
	}
	
	public List<E> getRows() {
		return this.fatchList();
	}
}

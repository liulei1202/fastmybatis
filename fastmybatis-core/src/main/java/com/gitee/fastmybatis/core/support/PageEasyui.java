package com.gitee.fastmybatis.core.support;

import com.gitee.fastmybatis.core.PageSupport;

import java.util.Collections;
import java.util.List;

/**
 * 支持easyui表格的json结果<br>
 * <code>{"total":28,"rows":[{...},{...}]}</code>
 * @param <E> 实体类
 * 
 * @author tanghc
 */
public class PageEasyui<E> extends PageSupport<E> {
    private static final long serialVersionUID = 2599057675920773433L;

	public PageEasyui() {
		this.setList(Collections.<E>emptyList());
	}

	public long getTotal() {
		return this.fatchTotal();
	}
	
	public List<E> getRows() {
		return this.fatchList();
	}
}

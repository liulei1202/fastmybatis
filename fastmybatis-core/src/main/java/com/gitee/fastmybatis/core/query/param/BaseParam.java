package com.gitee.fastmybatis.core.query.param;

import java.io.Serializable;

import com.gitee.fastmybatis.core.query.Query;

/**
 * @author tanghc
 */
@SuppressWarnings("serial")
public class BaseParam implements Serializable {

	/** 
	 * 生成Query查询对象
	 * @return 返回查询对象
	 */
	public Query toQuery() {
		return new Query().addAnnotionExpression(this);
	}
}

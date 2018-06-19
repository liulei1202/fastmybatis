package com.gitee.fastmybatis.core.query.param;

import com.gitee.fastmybatis.core.query.Query;

/**
 * @author tanghc
 */
public class BaseParam  {
    
	/** 
	 * 生成Query查询对象
	 * @return 返回查询对象
	 */
	public Query toQuery() {
		return new Query().addAnnotionExpression(this);
	}
}

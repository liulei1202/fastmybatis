package com.gitee.fastmybatis.core.query.param;

import com.gitee.fastmybatis.core.query.Query;

/**
 * @author tanghc
 */
public class BaseParam  {
	public Query toQuery() {
		return Query.build().addAnnotionExpression(this);
	}
}

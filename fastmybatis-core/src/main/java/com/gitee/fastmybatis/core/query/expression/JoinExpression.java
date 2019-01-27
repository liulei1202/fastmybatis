package com.gitee.fastmybatis.core.query.expression;

/**
 * @author tanghc
 */
public class JoinExpression implements ExpressionJoinable {

	private String joinSql;
	private int index = DEFAULT_INDEX;

	/**
	 * 自定义连接语句
	 * 
	 * @param joinSql
	 *            inner join table1 t1 on t.xx = t1.xx
	 */
	public JoinExpression(String joinSql) {
		this.joinSql = joinSql;
	}

	@Override
	public String getJoinSql() {
		return joinSql;
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public int index() {
		return index;
	}

}

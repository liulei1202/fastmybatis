package com.gitee.fastmybatis.core.query;

/**
 * 操作符
<table border="1">
<thead>
<tr>
<th>查询方式</th>
<th>说明</th>
</tr>
</thead>
<tbody>
<tr>
<td>eq</td>
<td>等于=</td>
</tr>
<tr>
<td>gt</td>
<td>大于&gt;</td>
</tr>
<tr>
<td>lt</td>
<td>小于&lt;</td>
</tr>
<tr>
<td>ge</td>
<td>大于等于&gt;=</td>
</tr>
<tr>
<td>le</td>
<td>小于等于&lt;=</td>
</tr>
<tr>
<td>notEq</td>
<td>不等于&lt;&gt;</td>
</tr>
<tr>
<td>like</td>
<td>模糊查询</td>
</tr>
<tr>
<td>in</td>
<td>in()查询</td>
</tr>
<tr>
<td>notIn</td>
<td>not in()查询</td>
</tr>
</tbody>
</table>
 * @author tanghc
 *
 */
public enum Operator {
	/** 等于= */
	eq("="),
	/** 大于&gt; */
	gt(">"),
	/** 小于&lt; */
	lt("<"),
	/** 大于等于&gt;= */
	ge(">="),
	/** 小于等于&lt;= */
	le("<="),
	/** 不等于&lt;&gt; */
	notEq("<>"),
	/** in()查询  */
	in("IN"),
	/** not in()查询 */
	notIn("NOT IN"),
	/** 模糊查询,两边模糊查询,like '%xx%' */
	like("LIKE"),
	/** 左模糊查询,like '%xx' */
	likeLeft("LIKE"),
	/** 右模糊查询,like 'xx%' */
	likeRight("LIKE"),
	
	nil(""),
	;
	
    
	private String operator;

	Operator(String operator) {
		this.operator = operator;
	}

	/**
	 * 返回操作符,{@literal =,>=,<... }
	 * @return 返回操作符
	 */
	public String getOperator() {
		return operator;
	}

}

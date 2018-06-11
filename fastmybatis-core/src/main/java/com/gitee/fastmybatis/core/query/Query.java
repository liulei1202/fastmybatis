package com.gitee.fastmybatis.core.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gitee.fastmybatis.core.SqlConsts;
import com.gitee.fastmybatis.core.query.expression.Expression;
import com.gitee.fastmybatis.core.query.expression.ExpressionJoinable;
import com.gitee.fastmybatis.core.query.expression.ExpressionListable;
import com.gitee.fastmybatis.core.query.expression.ExpressionSqlable;
import com.gitee.fastmybatis.core.query.expression.ExpressionValueable;
import com.gitee.fastmybatis.core.query.expression.Expressional;
import com.gitee.fastmybatis.core.query.expression.JoinExpression;
import com.gitee.fastmybatis.core.query.expression.ListExpression;
import com.gitee.fastmybatis.core.query.expression.SqlExpression;
import com.gitee.fastmybatis.core.query.expression.ValueConvert;
import com.gitee.fastmybatis.core.query.expression.ValueExpression;
import com.gitee.fastmybatis.core.query.expression.builder.ConditionBuilder;
import com.gitee.fastmybatis.core.query.param.BaseParam;
import com.gitee.fastmybatis.core.query.param.SchPageableParam;
import com.gitee.fastmybatis.core.query.param.SchSortableParam;

/**
 * 查询类
 * <pre>
根据主键查询：
User user = dao.get(1);

查询姓名为张三的用户：
User user = dao.getByProperty("username","张三");

查询姓名为张三的用户返回列表:
List<User> users = dao.listByProperty("username","张三");

查询姓名为张三，并且年龄为22岁的用户：
Query query = new Query().eq("username","张三").eq("age",22);
List<User> users = dao.find(query);

查询年龄为10,20,30的用户：
Query query = new Query().in("age",Arrays.asList(10,20,30));
List<User> users = dao.find(query);

查询注册日期大于2017-11-11的用户：
Date regDate = ...
Query query = new Query().gt("reg_date",regDate);
List<User> users = dao.find(query);

查询性别为男的，年龄大于等于20岁的用户，按年龄降序：
Query query = new Query().eq("gender",1).ge("age",20).orderby("age",Sort.DESC);
List<User> users = dao.find(query);

分页查询：
Query query = new Query().eq("age",10).page(1,10); // 第一页，每页10条数据
List<User> users = dao.find(query);

查询总记录数：
Query query = new Query().eq("age",10).page(1,10); // 第一页，每页10条数据
long total = dao.countTotal(query); // 该条件下总记录数
 * </pre>
 * @author tanghc
 *
 */
public class Query implements Queryable {

	private static final String REG_SQL_INJECT = "([';*--|])+";
	
	private int start;
	private int limit = 0;
	/** 排序信息 */
	private Set<String> orderInfo;

	private Map<String, Object> paramMap;

	private List<ExpressionValueable> valueExpressions;
	private List<ExpressionJoinable> joinExpressions;
	private List<ExpressionListable> listExpressions;
	private List<ExpressionSqlable> sqlExpressions;

	private List<String> columns = Collections.emptyList();
	private List<String> otherTableColumns;


	/**
	 * 将bean中的字段转换成条件,字段名会统一转换成下划线形式.已废弃，改用Query.buind(bean)
	 * <pre><code>
	 * User user = new User();
	 * user.setUserName("jim");
	 * Query query = Query.buildFromBean(user);
	 * </code>
	 * 这样会组装成一个条件:where user_name='jim'
	 * 更多功能可查看开发文档.
	 * </pre>
	 * @param bean
	 * @return
	 */
	private static Query buildFromBean(Object bean) {
		Query query = new Query();
		
		bindExpressionsFromBean(bean, query);
		
		return query;
	}
	
	private static void bindExpressionsFromBean(Object bean,Query query) {
		List<Expression> expresList = ConditionBuilder.getUnderlineFieldBuilder().buildExpressions(bean);

		for (Expression expression : expresList) {
			query.addExpression(expression);
		}
	}
	
	/**
	 * 将bean中的字段转换成条件,不会将字段名转换成下划线形式.
	 * <pre><code>
	 * User user = new User();
	 * user.setUserName("jim");
	 * Query query = Query.buildFromBeanByProperty(user);
	 * </code>
	 * 这样会组装成一个条件:where userName='jim'
	 * 更多功能可查看开发文档.
	 * </pre>
	 * @param bean
	 * @return
	 */
	public static Query buildFromBeanByProperty(Object bean) {
		Query query = new Query();
		List<Expression> expresList = ConditionBuilder.getCamelFieldBuilder().buildExpressions(bean);

		for (Expression expression : expresList) {
			query.addExpression(expression);
		}
		
		return query;
	
	}
	
	public static Query build() {
		return new Query();
	}

	/**
	 * 添加其它表的字段 query.addOtherColumn("t2.username as name");
	 * 
	 * @param column
	 * @return
	 */
	public Query addOtherColumn(String column) {
		if (otherTableColumns == null) {
			otherTableColumns = new ArrayList<>();
		}
		otherTableColumns.add(column);
		return this;
	}

	/**
	 * 批量添加其它字段.
	 * 
	 * query.addOtherColumns(
	 * "t2.username"
	 * ,"t2.userage as age"
	 * )
	 * 
	 * @param columns
	 * @return
	 */
	public Query addOtherColumns(String... columns) {
		for (String column : columns) {
			this.addOtherColumn(column);
		}
		return this;
	}

	@Override
	public List<String> getOtherTableColumns() {
		return otherTableColumns;
	}
	
	// ------ 设置分页信息 ------
	
	/**
     * 设置分页信息
     * 
     * @param pageIndex
     *            当前第几页,从1开始
     * @param pageSize
     *            每页结果集大小
     * @return 返回自身对象
     */
	public Query page(int pageIndex, int pageSize) {
	    this.setPage(pageIndex, pageSize);
	    return this;
	}
	
	/**
     * 设置分页信息,针对不规则分页。对应mysql分页语句：limit {start},{offset}
     * 
     * @param start
     *            记录起始位置
     * @param offset
     *            偏移量
     * @return 返回自身对象
     */
	public Query limit(int start, int offset) {
	    if(start < 0) {
	        throw new IllegalArgumentException("public Query limit(int start, int offset)方法start必须大于等于0");
	    }
	    if(offset < 1) {
	        throw new IllegalArgumentException("public Query limit(int start, int offset)方法offset必须大于等于1");
        }
	    this.start = start;
	    this.limit = offset;
	    return this;
	}


	/**
	 * 设置分页信息。已废弃，改用query.page(pageIndex,pageSize);
	 * 
	 * @param pageIndex
	 *            当前第几页,从1开始
	 * @param pageSize
	 *            每页结果集大小
	 * @return 返回自身对象
	 */
	private Query setPage(int pageIndex, int pageSize) {
		if (pageIndex < 1) {
			throw new IllegalArgumentException("pageIndex必须大于等于1");
		}
		if (pageSize < 1) {
            throw new IllegalArgumentException("pageSize必须大于等于1");
        }
		int start = (int) ((pageIndex - 1) * pageSize);
		int offset = pageSize;
		return this.limit(start, offset);
	}

	@Override
	public int getStart() {
		return this.start;
	}

	@Override
	public int getLimit() {
		return this.limit;
	}

	/**
	 * 
	 * 同getStart()
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return this.getStart();
	}

	/**
	 * 同getLimit()
	 * 
	 * @return
	 */
	public int getPageSize() {
		return this.getLimit();
	}

	// ------ 设置分页信息 end ------

	/**
	 * 构建查询全部的表达式对象
	 * 
	 * @return
	 */
	public static Query buildQueryAll() {
		return new Query();
	}

	/**
	 * 添加注解查询条件
	 * 
	 * @param searchEntity
	 * @return 返回自身对象
	 */
	public Query addAnnotionExpression(Object searchEntity) {
		bindExpressionsFromBean(searchEntity, this);
		return this;
	}

	/**
	 * 添加分页信息
	 */
	public Query addPaginationInfo(SchPageableParam searchEntity) {
		int start = searchEntity.getStart();
		int limit = searchEntity.getLimit();
		return this.limit(start, limit);
	}
	
	/** 
	 * 添加排序信息
	 * @param searchEntity
	 * @return 返回自身对象
	 */
	public Query addSortInfo(SchSortableParam searchEntity) {
		this.addSort(searchEntity.getDBSortname(), searchEntity.getSortorder());
		return this;
	}

	/**
	 * 构建查询条件.
	 * 
	 * @param searchEntity
	 * @return 
	 */
	public static Query build(Object searchEntity) {
	    if(searchEntity instanceof BaseParam) {
	        return ((BaseParam)searchEntity).toQuery();
	    }else {
	        return buildFromBean(searchEntity);
	    }
	}

	@Override
	public Expressional addExpression(Expression expression) {
		if (expression instanceof ExpressionValueable) {
			if(valueExpressions == null) {valueExpressions = new ArrayList<>();}
			valueExpressions.add((ExpressionValueable) expression);
		} else if (expression instanceof ExpressionListable) {
			if(listExpressions == null) {listExpressions = new ArrayList<>();}
			listExpressions.add((ExpressionListable) expression);
		} else if (expression instanceof ExpressionJoinable) {
			if(joinExpressions == null) {joinExpressions = new ArrayList<>();}
			joinExpressions.add((ExpressionJoinable) expression);
		} else if (expression instanceof ExpressionSqlable) {
			if(sqlExpressions == null) {sqlExpressions = new ArrayList<>();}
			sqlExpressions.add((ExpressionSqlable) expression);
		}

		return this;
	}

	public void addAll(List<Expression> expressions) {
		if (expressions != null) {
			for (Expression expression : expressions) {
				this.addExpression(expression);
			}
		}
	}

	/**
	 * 添加额外参数
	 * @param name
	 * @param value
	 * @return 返回自身对象
	 */
	public Query addParam(String name, Object value) {
		if (this.paramMap == null) {
			this.paramMap = new HashMap<>(16);
		}
		this.paramMap.put(name, value);
		return this;
	}

	@Override
	public Map<String, Object> getParam() {
		return this.paramMap;
	}

	@Override
	public boolean getIsQueryAll() {
		return this.limit == 0;
	}

	/**
	 * 查询全部
	 * @param queryAll true，则查询全部
	 * @return 返回自身对象
	 */
	public Query setQueryAll(boolean queryAll) {
	    if(queryAll) {
	        this.limit = 0;
	    }
	    return this;
	}

	@Override
	public List<ExpressionValueable> getValueExpressions() {
		return this.valueExpressions;
	}

	@Override
	public List<ExpressionJoinable> getJoinExpressions() {
		return this.joinExpressions;
	}

	@Override
	public List<ExpressionListable> getListExpressions() {
		return this.listExpressions;
	}

	@Override
	public List<ExpressionSqlable> getSqlExpressions() {
		return this.sqlExpressions;
	}
	
	/**
     * 字段排序
     * @param sortname 数据库字段名
     * @param sort 排序类型
     * @return
     */
	public Query orderby(String sortname, Sort sort) {
	    return this.addSort(sortname, sort);
	}

	/**
	 * 添加ASC排序字段,
	 * 
	 * @param sortname
	 *            数据库字段名
	 * @return 返回自身对象
	 */
	public Query addSort(String sortname) {
		return this.addSort(sortname, SqlConsts.ASC);
	}
	
	/**
	 * 字段排序
	 * @param sortname 数据库字段名
	 * @param sort 排序类型
	 * @return
	 */
	public Query addSort(String sortname, Sort sort) {
		return this.addSort(sortname, sort.name());
	}

	/**
	 * 添加排序字段。
	 * 已废弃，推荐用：public Query addSort(String sortname, Sort sort)
	 * @param sortname
	 *            数据库字段名
	 * @param sortorder
	 *            排序方式,ASC,DESC
	 * @return 返回自身对象
	 */
	private Query addSort(String sortname, String sortorder) {

		if (sortname != null && sortname.length() > 0) {
			if (this.orderInfo == null) {
				orderInfo = new LinkedHashSet<String>();
			}
			// 简单防止SQL注入
			sortname = sortname.replace(REG_SQL_INJECT, SqlConsts.EMPTY);

			if (!SqlConsts.DESC.equalsIgnoreCase(sortorder)) {
				sortorder = SqlConsts.ASC;
			}

			orderInfo.add(sortname + SqlConsts.BLANK + sortorder);
		}

		return this;
	}

	@Override
	public boolean getOrderable() {
		return orderInfo != null;
	}

	@Override
	public String getOrder() {
		if(orderInfo == null) {
			throw new NullPointerException("orderInfo为空,必须设置排序字段.");
		}
		StringBuilder sb = new StringBuilder();
		for (String order : this.orderInfo) {
			sb.append(",").append(order);
		}
		if (sb.length() > 0) {
			return sb.toString().substring(1);
		} else {
			return "";
		}
	}

	@Override
	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	//

	/**
	 * 添加关联条件
	 * 
	 * @param joinSql
	 * @return
	 */
	public Query join(String joinSql) {
		this.addExpression(new JoinExpression(joinSql));
		return this;
	}
	
	/**
	 * 使用key/value进行多个等于的比对,相当于多个eq的效果
	 * @param map
	 * @return
	 */
	public Query allEq(LinkedHashMap<String, Object> map) {
		Set<String> keys = map.keySet();
		for (String columnName : keys) {
			this.eq(columnName, map.get(columnName));
		}
		return this;
	}
	
	/**
	 * 添加等于条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return 返回自身对象
	 */
	public Query eq(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, value));
		return this;
	}

	/**
	 * 添加不等于条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return 返回自身对象
	 */
	public Query notEq(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, "<>", value));
		return this;
	}

	/**
	 * 添加大于条件,>
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return 返回自身对象
	 */
	public Query gt(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, ">", value));
		return this;
	}

	/**
	 * 大于等于,>=
	 * @param columnName
	 * @param value
	 * @return 返回自身对象
	 */
	public Query ge(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, ">=", value));
		return this;
	}

	/**
	 * 添加小于条件,<
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return 返回自身对象
	 */
	public Query lt(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, "<", value));
		return this;
	}

	/**
	 * 小于等于,<=
	 * @param columnName
	 * @param value
	 * @return 返回自身对象
	 */
	public Query le(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, "<=", value));
		return this;
	}
	
	/**
	 * 添加模糊查询条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return 返回自身对象
	 */
	public Query like(String columnName, Object value) {
		this.addExpression(new ValueExpression(columnName, SqlConsts.LIKE, value));
		return this;
	}

	/**
	 * 添加IN条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return 返回自身对象
	 */
	public Query in(String columnName, Collection<?> value) {
		return this.in(columnName, value, null);
	}

	/**
	 * 添加IN条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return 返回自身对象
	 */
	public <T> Query in(String columnName, Collection<T> value, ValueConvert<T> valueConvert) {
		if (value == null || value.size() == 0) {
			throw new RuntimeException("查询条件:[" + columnName + " in(...)]" + "至少要有一个值.");
		}

		Expression expression = valueConvert == null ? new ListExpression(columnName, value)
				: new ListExpression(columnName, value, valueConvert);

		this.addExpression(expression);

		return this;
	}

	/**
	 * 添加IN条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return 返回自身对象
	 */
	public Query in(String columnName, Object[] value) {
		if (value == null || value.length == 0) {
			throw new RuntimeException("查询条件:[" + columnName + " in(...)]" + "至少要有一个值.");
		}
		this.addExpression(new ListExpression(columnName, value));
		return this;
	}

	/**
	 * 添加not in条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return 返回自身对象
	 */
	public Query notIn(String columnName, Collection<?> value) {
		this.addExpression(new ListExpression(columnName, "NOT IN", value));
		return this;
	}

	/**
	 * 添加not in条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return 返回自身对象
	 */
	public <T> Query notIn(String columnName, Collection<T> value, ValueConvert<T> valueConvert) {
		this.addExpression(new ListExpression(columnName, "NOT IN", value, valueConvert));
		return this;
	}

	/**
	 * 添加not in条件
	 * 
	 * @param columnName
	 *            数据库字段名
	 * @param value
	 *            值
	 * @return 返回自身对象
	 */
	public Query notIn(String columnName, Object[] value) {
		this.addExpression(new ListExpression(columnName, "NOT IN", value));
		return this;
	}

	/**
	 * 添加自定义sql条件
	 * 
	 * @param sql
	 *            自定义sql
	 * @return 返回自身对象
	 */
	public Query sql(String sql) {
		this.addExpression(new SqlExpression(sql));
		return this;
	}

	/**
	 * 字段不为null的条件
	 * @param column 数据库字段名
	 * @return 返回自身对象
	 */
	public Query notNull(String column) {
		return this.sql(column + " IS NOT NULL");
	}

	/**
	 * 字段是null的条件
	 * @param column 数据库字段名
	 * @return 返回自身对象
	 */
	public Query isNull(String column) {
		return this.sql(column + " IS NULL");
	}

	/**
	 * 不为空字符串
	 * @param column 数据库字段名
	 * @return 返回自身对象
	 */
	public Query notEmpty(String column) {
		return this.sql(column + " IS NOT NULL AND " + column + " <> '' ");
	}

	/**
	 * 空字段条件，null或者空字符串
	 * @param column 数据库字段名
	 * @return 返回自身对象
	 */
	public Query isEmpty(String column) {
		return this.sql(column + " IS NULL OR " + column + " = '' ");
	}

	/**
	 * 不等于条件
	 * @return 返回自身对象
	 */
	public Query notEq() {
		return this.sql("1=2");
	}

}

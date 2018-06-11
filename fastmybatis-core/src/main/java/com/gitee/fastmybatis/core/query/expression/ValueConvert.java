package com.gitee.fastmybatis.core.query.expression;

/**
 * @author tanghc
 */
public interface ValueConvert<T> {
	/**
	 * 转换对象
	 * @param obj 实体类对象
	 * @return 返回新的值
	 */
	Object convert(T obj);
}

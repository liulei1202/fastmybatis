package com.gitee.fastmybatis.core.handler;

import java.lang.reflect.Field;

/**
 * 数据填充父类
 * @author tanghc
 *
 * @param <T> 填充字段类型，如Date，String，BigDecimal，Boolean
 */
public abstract class BaseFill<T> extends AbstractTypeHandlerAdapter<T> {
	

	/**
	 * 字段名
	 * @return
	 */
	public abstract String getColumnName();
	
	/**
	 * 操作类型<br>
	 * 字段插入后不能修改用FillType.INSERT，如：create_time字段<br>
	 * 字段插入后能修改用FillType.UPDATE，如：update_time字段
	 * @return
	 */
	public abstract FillType getFillType();
	
	/**
	 * 作用在指定实体类上，返回null或空则作用在所有实体类。
	 * @return 返回实体类的class数组
	 */
	public Class<?>[] getTargetEntityClasses() {
		return null;
	}
	
	/**
	 * 优先值，越小的值优先
	 * @return
	 */
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
	
	/**
	 * 是否能够作用到指定字段
	 * @param entityClass 实体类class
	 * @param field 字段信息
	 * @param columnName 给定的数据库字段名
	 * @return
	 */
	public boolean match(Class<?> entityClass, Field field, String columnName) {
		boolean isTargetClass = this.containClass(entityClass);
		boolean isTargetColumn = match(columnName) || match(field.getName());
		return isTargetClass && isTargetColumn;
	}
	
	/**
	 * 是否能够作用到指定字段
	 * @param columnName 给定的数据库字段名
	 * @return
	 */
	public boolean match(String columnName) {
		return this.getColumnName().equals(columnName);
	}
	
	/**
	 * 是否作用在entityClass上
	 * @param entityClass 给定的entityClass
	 * @return
	 */
	public boolean containClass(Class<?> entityClass) {
		Class<?>[] classes = this.getTargetEntityClasses();
		if(classes == null || classes.length == 0) {
			return true;
		}
		for (Class<?> targetClass : classes) {
			if(targetClass == entityClass) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected T convertValue(Object columnValue) {
		return (T)columnValue;
	}
	
}

package com.gitee.fastmybatis.core.ext;

import java.util.HashMap;
import java.util.Map;

import com.gitee.fastmybatis.core.util.ClassUtil;

/**
 * @author tanghc
 */
public class ExtContext {
	
	private static Map<String, Class<?>> entityMapper = new HashMap<>(16);
	
	public static void addMapperClass(Class<?> mapperClass) {
		Class<?> entityClass = getEntityClass(mapperClass);
		entityMapper.put(entityClass.getName(), mapperClass);
	}
	
	public static Class<?> getMapperClass(Class<?> entityClass) {
		return entityMapper.get(entityClass.getName());
	}
	
	public static Class<?> getEntityClass(Class<?> mapperClass) {
		if (mapperClass.isInterface()) {
			return ClassUtil.getSuperInterfaceGenricType(mapperClass, 0);
		} else {
			return ClassUtil.getSuperClassGenricType(mapperClass, 0);
		}
	}
}

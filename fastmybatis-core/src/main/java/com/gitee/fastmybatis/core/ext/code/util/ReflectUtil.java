package com.gitee.fastmybatis.core.ext.code.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 方法类
 * 
 * @author tanghc
 * 
 */
public class ReflectUtil {
	
	private ReflectUtil() {
		super();
	}

	private static final String SERIAL_VERSION_UID_NAME = "serialVersionUID";

	/**
	 * 循环向上转型, 获取对象的所有的DeclaredField
	 * 
	 * @param clazz
	 *            : 子类对象
	 * @return 父类中的属性对象
	 */

	public static List<Field> getDeclaredFields(Class<?> clazz) {
		List<Field> fieldList = new ArrayList<>();
		Field[] fields = null;
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				fields = clazz.getDeclaredFields();
				for (int i = 0, size = fields.length; i < size; i++) {
					Field field = fields[i];
					// 过滤serialVersionUID字段
					if(Modifier.isStatic(field.getModifiers()) && SERIAL_VERSION_UID_NAME.equals(field.getName())) {
						continue;
					}
					fieldList.add(field);
				}
			} catch (Exception e) {
				// 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
				// 如果这里的异常打印或者往外抛，则就不会执行clazz =
				// clazz.getSuperclass(),最后就不会进入到父类中了
			}
		}

		return fieldList;
	}

	/**
	 * 循环向上转型, 获取对象的 DeclaredField
	 * 
	 * @param object
	 *            : 子类对象
	 * @param fieldName
	 *            : 父类中的属性名
	 * @return 父类中的属性对象
	 */

	public static Field getDeclaredField(Object object, String fieldName) {
		Field field = null;

		Class<?> clazz = object.getClass();

		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				return field;
			} catch (Exception e) {
				// 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
				// 如果这里的异常打印或者往外抛，则就不会执行clazz =
				// clazz.getSuperclass(),最后就不会进入到父类中了

			}
		}

		return null;
	}
}

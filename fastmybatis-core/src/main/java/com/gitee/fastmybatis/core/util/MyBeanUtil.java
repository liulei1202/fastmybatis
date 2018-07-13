package com.gitee.fastmybatis.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 对象拷贝
 * @author tanghc
 */
public class MyBeanUtil extends org.springframework.beans.BeanUtils {
    
    private static final String PREFIX_SET = "set";

	/**
	 * 属性拷贝,第一个参数中的属性值拷贝到第二个参数中<br>
	 * 注意:当第一个参数中的属性有null值时,不会拷贝进去
	 * @param source 源对象
	 * @param target 目标对象
	 * @throws BeansException
	 */
	public static void copyProperties(Object source, Object target)
			throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(
						source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass()
								.getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断value是否为空 当然这里也能进行一些特殊要求的处理
						// 例如绑定时格式转换等等
						if (value != null) {
							if (!Modifier.isPublic(writeMethod
									.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}
					} catch (Throwable ex) {
						throw new FatalBeanException(
								"Could not copy properties from source to target field name mismatch:" + targetPd.getName(),
								ex);
					}
				}
			}
		}
	}
	

    /**
     * 属性拷贝，把map中的值拷贝到target中去
     * 
     * @param map
     * @param target
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void copyProperties(Map<String, Object> map, Object target) {
        Assert.notNull(map, "map must not be null");
        Assert.notNull(target, "Target must not be null");

        if (target instanceof Map) {
            Map targetMap = (Map) target;
            try {
                targetMap.putAll(map);
            }catch (Exception e) {
                throw new FatalBeanException("target的key类型必须为String，value类型为Object");
            }
        } else {
            Set<Entry<String, Object>> entrySet = map.entrySet();
            Class<?> targetClass = target.getClass();

            for (Entry<String, Object> entry : entrySet) {
                String propertyName = entry.getKey();
                Object val = entry.getValue();
                Method[] methods = targetClass.getDeclaredMethods();
                for (Method method : methods) {
                    String methodName = method.getName();
                    Class<?>[] methodParams = method.getParameterTypes();
                    // set开头
                    if (methodName.startsWith(PREFIX_SET)) {
                        // 能否拷贝
                        boolean canCopy = propertyName.equals(buildFieldName(methodName)) // 字段名一样
                                && methodParams.length == 1 // 并且只有一个参数
                                && methodParams[0].isInstance(val); // val是methodParams[0]或他的子类
                        
                        if (canCopy) {
                            try {
                                if (!Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
                                    method.setAccessible(true);
                                }
                                method.invoke(target, val);
                            } catch (Throwable ex) {
                                throw new FatalBeanException(
                                        "Could not copy property '" + propertyName + "' from map to target", ex);
                            }
                        }
                    }
                }
            }
        }

    }
    
    /**
     * 将实体对象转换成Map
     * 
     * @param pojo
     *            实体类
     * @return 返回map
     */
    public static Map<String, Object> pojoToMap(Object pojo) {
        if (pojo == null) {
            return Collections.emptyMap();
        }
        String json = JSON.toJSONString(pojo);
        return JSON.parseObject(json);
    }
    
    /**
     * 将map对象转换成普通类
     * 
     * @param map
     *            map对象
     * @param pojoClass
     *            普通类
     * @return 返回普通类
     */
    public static <T> T mapToPojo(Map<String, Object> map, Class<T> pojoClass) {
        return new JSONObject(map).toJavaObject(pojoClass);
    }

    /**
     * map集合转换成对象集合
     * 
     * @param list
     *            map集合
     * @param pojoClass
     *            待转换的对象类型
     * @return 返回对象集合
     */
    public static <T> List<T> mapListToObjList(List<Map<String, Object>> list, Class<T> pojoClass) {
        if (list == null) {
            return Collections.emptyList();
        }
        List<T> retList = new ArrayList<>(list.size());
        for (Map<String, Object> map : list) {
            retList.add(mapToPojo(map, pojoClass));
        }
        return retList;
    }


    // 构建列名
    private static String buildFieldName(String methodName) {
        return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
    }
}

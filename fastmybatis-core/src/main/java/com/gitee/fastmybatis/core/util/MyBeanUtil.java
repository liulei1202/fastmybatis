package com.gitee.fastmybatis.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.fastmybatis.core.exception.QueryException;
import com.gitee.fastmybatis.core.ext.code.util.FieldUtil;

/**
 * 对象拷贝
 * @author tanghc
 */
public class MyBeanUtil extends org.springframework.beans.BeanUtils {
	
	private static final String PREFIX_GET = "get";
	private static final String GETCLASS_NAME = "getClass";
	
	// key:clazz.getName();
	private static Map<String, List<Method>> objGetMethods = new ConcurrentHashMap<>(16);
    
	/**
     * 属性拷贝,第一个参数中的属性值拷贝到第二个参数中<br>
     * 注意:当第一个参数中的属性有null值时,不会拷贝进去
     * @param source 源对象
     * @param target 目标对象
     * @throws BeansException
     */
	public static void copyPropertiesIgnoreNull(Object source, Object target)
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
     * @param map map对象
     * @param target 目标对象
     */
    public static void copyPropertiesForMap(Map<String, Object> map, Object target) {
        Assert.notNull(map, "map must not be null");
        Assert.notNull(target, "Target must not be null");
        Object pojo = mapToPojo(map, target.getClass());
        copyProperties(pojo, target);
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
     * 将pojo对象中的字段值和名称存到map中，并且字段值转成数据库字段名
     * @param pojo
     * @return 返回map，key为数据库字段名
     */
    public static Map<String, Object> createUpdateMap(Object pojo) {
    	return doCreateUpdateMap(pojo, false);
    }
    
    /**
     * 将pojo对象中的字段值和名称存到map中，并且字段值转成数据库字段名。忽略null字段
     * @param pojo
     * @return 返回map，key为数据库字段名
     */
    public static Map<String, Object> createUpdateMapIgnoreNull(Object pojo) {
    	return doCreateUpdateMap(pojo, true);
    }
    
    public static Map<String, Object> doCreateUpdateMap(Object pojo, boolean ignoreNull) {
    	Class<?> clazz = pojo.getClass();
    	String key = clazz.getName();
    	List<Method> methods = objGetMethods.get(key);
    	if(methods == null) {
    		methods = new ArrayList<>();
    		Method[] clazzMethods = clazz.getMethods();
        	for (Method method : clazzMethods) {
    			if(isGetMethod(method)) {
    				methods.add(method);
    			}
    		}
        	objGetMethods.put(key, methods);
    	}
    	
    	if(CollectionUtils.isEmpty(methods)) {
    		return Collections.emptyMap();
    	}
    	Map<String, Object> ret = new HashMap<>(8);
    	try {
	    	for (Method method : methods) {
	    		Object value = method.invoke(pojo);
	    		if(value != null || !ignoreNull) {
	    			String columnName = method.getName().substring(3);
	    			ret.put(FieldUtil.camelToUnderline(columnName), value);
	    		}
			}
    	} catch (Exception e) {
			throw new QueryException(e);
		}
    	return ret;
    }
    
    /** 是否是get方法 */
	public static boolean isGetMethod(Method method) {
	    if(method.getReturnType() == Void.TYPE) {
	        return false;
	    }
	    String methodName = method.getName();
		return (!GETCLASS_NAME.equals(methodName)) && methodName.startsWith(PREFIX_GET);
	}
    
    /**
     * 将map对象转换成普通类
     * 
     * @param <T> 普通类类型
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
     * @param <T> 普通类类型
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
    
}

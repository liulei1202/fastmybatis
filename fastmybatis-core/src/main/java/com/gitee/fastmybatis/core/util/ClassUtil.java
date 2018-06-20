package com.gitee.fastmybatis.core.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author tanghc
 */
public class ClassUtil {

    private ClassUtil() {
    };

    private static final String PREFIX_GET = "get";

    /**
     * 返回定义类时的泛型参数的类型. <br>
     * 如:定义一个BookManager类<br>
     * <code>{@literal public BookManager extends GenricManager<Book,Address>}{...} </code>
     * <br>
     * 调用getSuperClassGenricType(getClass(),0)将返回Book的Class类型<br>
     * 调用getSuperClassGenricType(getClass(),1)将返回Address的Class类型
     * 
     * @param clazz
     *            从哪个类中获取
     * @param index
     *            泛型参数索引,从0开始
     */
    public static Class<?> getSuperClassGenricType(Class<?> clazz, int index) throws IndexOutOfBoundsException {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    /**
     * 返回接口类的泛型参数的类型
     * 
     * @param clazz
     * @param index
     * @return 返回class对象
     */
    public static Class<?> getSuperInterfaceGenricType(Class<?> clazz, int index) {
        // 一个类可能实现多个接口,每个接口上定义的泛型类型都可取到
        Type[] interfacesTypes = clazz.getGenericInterfaces();
        if (interfacesTypes.length == 0) {
            return Object.class;
        }
        // 取第一个接口
        Type firstInterface = interfacesTypes[0];
        Type[] params = ((ParameterizedType) firstInterface).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class<?>) params[index];
    }

    /**
     * 返回类名并且第一个字母小写
     * 
     * @param clazz
     * @return 返回类名并且第一个字母小写
     */
    public static String getClassSimpleName(Class<?> clazz) {
        String className = clazz.getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
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
        Method[] methods = pojo.getClass().getDeclaredMethods();
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            for (Method method : methods) {
                String methodName = method.getName();

                if (methodName.startsWith(PREFIX_GET) && method.getParameterTypes().length == 0) {
                    String fieldName = buildFieldName(methodName);
                    Object value = method.invoke(pojo);
                    map.put(fieldName, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("pojoToMap失败", e);
        }

        return map;
    }
    

    /**
     * map集合转换成对象集合
     * @param list map集合
     * @param pojoClass 待转换的对象类型
     * @return 返回对象集合
     */
    public static <T> List<T> mapListToObjList(List<Map<String, Object>> list, Class<T> pojoClass) {
        if(list == null) {
            return Collections.emptyList();
        }
        List<T> retList = new ArrayList<>(list.size());
        for (Map<String, Object> map : list) {
            retList.add(mapToPojo(map, pojoClass));
        }
        return retList;
    }
    
    /**
     * 将map对象转换成普通类
     * @param map map对象
     * @param pojoClass 普通类
     * @return 返回普通类
     */
    public static <T> T mapToPojo(Map<String, Object> map, Class<T> pojoClass) {
        try {
            T target = pojoClass.newInstance();
            MyBeanUtil.copyProperties(map, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("实例化失败", e);
        } 
    }

    /**
     * 是否是数组或结合类
     * @param value 待检测对象
     * @return true，是结合或数组
     */
    public static boolean isArrayOrCollection(Object value) {
        boolean ret = false;
        if (value.getClass().isArray()) {
            ret = true;
        } else if (value instanceof Collection) {
            ret = true;
        }
        return ret;
    }
    
    // 构建列名
    private static String buildFieldName(String methodName) {
        return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
    }
    
}

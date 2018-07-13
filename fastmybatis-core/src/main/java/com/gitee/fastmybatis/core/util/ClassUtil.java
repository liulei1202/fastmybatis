package com.gitee.fastmybatis.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author tanghc
 */
public class ClassUtil {

    private ClassUtil() {
    };

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
     * 将实体对象转换成Map。已废弃，使用MyBeanUtil.pojoToMap(pojo)
     * 
     * @param pojo
     *            实体类
     * @return 返回map
     */
    @Deprecated
    public static Map<String, Object> pojoToMap(Object pojo) {
        return MyBeanUtil.pojoToMap(pojo);
    }

    /**
     * map集合转换成对象集合。已废弃，使用MyBeanUtil.mapListToObjList(list, pojoClass);
     * 
     * @param list
     *            map集合
     * @param pojoClass
     *            待转换的对象类型
     * @return 返回对象集合
     */
    @Deprecated
    public static <T> List<T> mapListToObjList(List<Map<String, Object>> list, Class<T> pojoClass) {
        return MyBeanUtil.mapListToObjList(list, pojoClass);
    }

    /**
     * 将map对象转换成普通类。已废弃，使用MyBeanUtil.mapToPojo(map, pojoClass);
     * 
     * @param map
     *            map对象
     * @param pojoClass
     *            普通类
     * @return 返回普通类
     */
    @Deprecated
    public static <T> T mapToPojo(Map<String, Object> map, Class<T> pojoClass) {
        return MyBeanUtil.mapToPojo(map, pojoClass);
    }

    /**
     * 是否是数组或结合类
     * 
     * @param value
     *            待检测对象
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

}

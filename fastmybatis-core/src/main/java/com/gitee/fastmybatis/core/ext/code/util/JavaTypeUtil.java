package com.gitee.fastmybatis.core.ext.code.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * java字段类型管理,主要负责基本类型,装箱类型,mybatis类型之间的映射
 * 
 * @author tanghc
 *
 */
public class JavaTypeUtil {

	/** key:基本类型 */
	private static Map<String, JavaType> javaTypeMap = new HashMap<>();
	/** key:基本类型或装箱类型 value:mybatis类型 */
	private static Map<String, String> mybatisTypeMap = new HashMap<>();
	/** 基本类型和装箱类型集合 */
	private static Set<String> javaTypeSet = new HashSet<>();
	
	static {
		//         	基本类型           		装箱类型		mybatis类型
		addJavaType("boolean"	, "Boolean"		, "BIT");
		addJavaType("byte"		, "Byte"		, "TINYINT");
		addJavaType("short"		, "Short"		, "SMALLINT");
		addJavaType("char"		, "Character"	, "VARCHAR");
		addJavaType("int"		, "Integer"		, "INTEGER");
		addJavaType("long"		, "Long"		, "BIGINT");
		addJavaType("float"		, "Float"		, "FLOAT");
		addJavaType("double"	, "Double"		, "DOUBLE");
		
		addJavaType("byte[]"	, "Byte[]"		, "BINARY");
		
		addJavaType("String"	, "String"		, "VARCHAR");
		addJavaType("Date"		, "Date"		, "TIMESTAMP");
		addJavaType("Time"		, "Time"		, "TIMESTAMP");
		addJavaType("Timestamp"	, "Timestamp"	, "TIMESTAMP");
		addJavaType("BigDecimal", "BigDecimal"	, "DECIMAL");
		addJavaType("Clob"		, "Clob"		, "CLOB");
		addJavaType("Blob"		, "Blob"		, "BLOB");
		
		initMybatisTypeMap();
	}

	/** 初始化mybatis类型
	  基本类型和装箱类型指向同一个mybatis类型 */
	private static void initMybatisTypeMap() {
		Set<Entry<String, JavaType>> entrySet = javaTypeMap.entrySet();
		String type, boxType, mybatisType;
		for (Entry<String, JavaType> entry : entrySet) {
			type = entry.getKey();
			boxType = entry.getValue().getBoxType();
			mybatisType = entry.getValue().getMybatisType();

			addMybatisType(type, mybatisType);
			addMybatisType(boxType, mybatisType);
		}
	}

	/**
	 * 添加Java类型对应的mybatis类型
	 * 
	 * @param javaType
	 *            Java基本类型或装箱类型
	 * @param mybatisType
	 *            mybatis类型
	 */
	public static void addMybatisType(String javaType, String mybatisType) {
		mybatisTypeMap.put(javaType, mybatisType);
	}

	/**
	 * 添加java类型映射
	 * 
	 * @param baseType
	 *            基本类型
	 * @param boxType
	 *            装箱类型
	 * @param mybatisType
	 *            mybatis类型
	 */
	public static void addJavaType(String baseType, String boxType, String mybatisType) {
		javaTypeMap.put(baseType, new JavaType(baseType, boxType, mybatisType));

		addJavaType(baseType);
		addJavaType(boxType);
	}

	/**
	 * 将基本类型转换为装箱类型
	 * 
	 * @param baseType
	 *            java基本类型
	 * @return 找不到返回自身
	 */
	public static String convertToJavaBoxType(String baseType) {
		JavaType type = javaTypeMap.get(baseType);
		return type == null ? baseType : type.getBoxType();
	}

	/**
	 * 将Java类型转换为mybatis的jdbcType
	 * 
	 * @param javaType
	 *            基本类或装箱类型
	 * @return 找不到类型默认返回VARCHAR
	 */
	public static String convertToMyBatisJdbcType(String javaType) {
		String mybatisJdbcType = mybatisTypeMap.get(javaType);
		return mybatisJdbcType == null ? "VARCHAR" : mybatisJdbcType;
	}

	/**
	 * 添加java字段类型
	 * @param type 基本类型或装箱类型
	 */
	public static void addJavaType(String type) {
		javaTypeSet.add(type);
	}
	
	/**
	 * 返回java字段类型
	 * @return
	 */
	public static Set<String> getJavaTypes() {
		return javaTypeSet;
	}
	
	public static boolean isJavaType(String type) {
		return javaTypeSet.contains(type);
	}
	
}

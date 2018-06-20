package com.gitee.fastmybatis.core.ext.code.generator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.StringUtils;

import com.gitee.fastmybatis.core.FastmybatisConfig;
import com.gitee.fastmybatis.core.annotation.LogicDelete;
import com.gitee.fastmybatis.core.ext.code.util.FieldUtil;
import com.gitee.fastmybatis.core.ext.code.util.JavaTypeUtil;
import com.gitee.fastmybatis.core.ext.code.util.ReflectUtil;
import com.gitee.fastmybatis.core.handler.BaseEnum;
import com.gitee.fastmybatis.core.handler.BaseFill;
import com.gitee.fastmybatis.core.handler.EnumTypeHandler;
import com.gitee.fastmybatis.core.handler.FillType;

/**
 * 表信息查询
 * @author tanghc
 */
public class ColumnSelector {

	private final static String UUID_NAME = "uuid";
	private final static String INCREMENT_NAME = "increment";
	private final static String STRING_TYPE = "String";
	
	private Class<?> entityClass;
	private FastmybatisConfig config;
	
	public ColumnSelector(Class<?> entityClass,FastmybatisConfig config) {
		super();
		this.entityClass = entityClass;
		this.config = config;
	}

	private String getColumnType(Field field) {
		return field.getType().getSimpleName();
	}
	
	private String getColumnFullType(Field field) {
		return field.getType().getName();
	}
	
	private boolean isEnum(Field field) {
		Class<?> enumType = field.getType();
		boolean isEnum = enumType.isEnum();
		if(isEnum) {
			this.checkEnumn(enumType);
		}
		return isEnum;
	}
	
	private void checkEnumn(Class<?> enumType) {
		boolean isBaseEnum = false;
		Class<?> baseEnumClass = BaseEnum.class;
		Type[] arr = enumType.getInterfaces();
		
		for (Type type : arr) {
			if(type.equals(baseEnumClass)) {
				isBaseEnum = true;
				break;
			}
		}
		
		if(!isBaseEnum) {
			throw new RuntimeException("枚举类：" + enumType.getName() + "必须实现" + baseEnumClass.getName() + "接口");
		}
	}
	
	/** 根据java字段获取数据库字段名 */
	private String getColumnName(Field field) {
		Column columnAnno = field.getAnnotation(Column.class);
		// 存在注解
		if(columnAnno != null) {
			String columnName = columnAnno.name();
			if("".equals(columnName)) {
				throw new IllegalArgumentException(field.getName() + "注解@Column(name=\"\")name属性不能为空");
			}
			return columnName;
		}
		
		String javaFieldName = field.getName();
		// 如果开启了驼峰转下划线形式
		if(config.isCamel2underline()) {
			return FieldUtil.camelToUnderline(javaFieldName);
		}else {
			return javaFieldName;
		}
		
	}
	
	private boolean isPK(Field field) {
		return field.getAnnotation(Id.class) != null;
	}
	
	/** 是否主键自增 */
	private boolean isIdentity(GeneratedValue generatedValue) {
		String generator = generatedValue.generator().toLowerCase();
		return (generatedValue.strategy() == GenerationType.IDENTITY || generator.contains(INCREMENT_NAME));
	}
	
	private boolean isUuid(Field field,String columnType,GeneratedValue generatedValue) {
		String generator = generatedValue.generator().toLowerCase();
		
		boolean isUuid = generator.contains(UUID_NAME);
		boolean isStringType = STRING_TYPE.equals(columnType);
		
		// 如果定义了UUID策略，但类型不是String
		if(isUuid && !isStringType) {
			String columnName = this.getColumnName(field);
			throw new RuntimeException("字段[" + columnName + "]定义了UUID策略，但类型不是String，实际类型为：" + columnType);
		}
		
		return isUuid && isStringType;
	}
	
	public List<ColumnDefinition> getColumnDefinitions() {
		List<ColumnDefinition> columnDefinitionList = new ArrayList<ColumnDefinition>();
		// 构建columnDefinition
		
		List<Field> fields = ReflectUtil.getDeclaredFields(entityClass);
		
		for (Field field : fields) {
			ColumnDefinition columnDefinition = buildColumnDefinition(field);
			if(columnDefinition != null) {
				columnDefinitionList.add(columnDefinition);
			}
		}
					
		return columnDefinitionList;
	}
	
	/**
	 * 构建列信息
	 * @param field 字段信息
	 * @return 返回构建列信息
	 */
	protected ColumnDefinition buildColumnDefinition(Field field) {
		ColumnDefinition columnDefinition = new ColumnDefinition();
		
		Transient transientAnno = field.getAnnotation(Transient.class);
		boolean isTransient = transientAnno != null ? true : Modifier.isTransient(field.getModifiers());
		columnDefinition.setTransient(isTransient);
		
		String columnName = this.getColumnName(field);
		String columnType = this.getColumnType(field);
		String fullType = this.getColumnFullType(field);
		boolean isEnum = this.isEnum(field);
		// 不是枚举,也不是java类型
		if(!isEnum && !JavaTypeUtil.isJavaType(columnType)) {
			return null;
		}
		
		columnDefinition.setJavaFieldName(field.getName());
		columnDefinition.setColumnName(columnName);
		columnDefinition.setType(columnType);
		columnDefinition.setFullType(fullType);
		columnDefinition.setEnum(isEnum);
		
		if(isEnum) {
			columnDefinition.setTypeHandler(EnumTypeHandler.class.getName());
			columnDefinition.setFillType(FillType.UPDATE);
		}
		
		boolean isPk = this.isPK(field);
		columnDefinition.setIsPk(isPk);
		
		if(isPk) {
			// 设置主键策略
			GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
			if(generatedValue == null) {
				throw new NullPointerException("class[" + entityClass.getName() + "." + columnName + "]未设置主键策略，是否缺少@GeneratedValue注解");
			}
			// 是否是自增
			if(this.isIdentity(generatedValue)) { 
				columnDefinition.setIsIdentity(true);
			} else { // 是否是uuid策略
				boolean isUuid = this.isUuid(field, columnType, generatedValue);
				columnDefinition.setIsUuid(isUuid);
			}
		}
		
		boolean isVersionColumn = this.isVersionColumn(field);
		columnDefinition.setIsVersion(isVersionColumn);
		
		this.bindLogicDeleteColumnInfo(columnDefinition, field);
		
		if(!isTransient) {
			this.bindFill(columnDefinition,field);
		}
		
		return columnDefinition;
	}
	
	/** 绑定逻辑删除字段信息 */
	private void bindLogicDeleteColumnInfo(ColumnDefinition columnDefinition,Field field) {
		LogicDelete logicDelete = field.getAnnotation(LogicDelete.class);
		boolean isLogicDelete = logicDelete != null;
		columnDefinition.setIsLogicDelete(isLogicDelete);
		
		if(isLogicDelete) {
			Object delVal = null,notDelVal = null;
			String deleteValue = logicDelete.deleteValue();
			String notDeleteValue = logicDelete.notDeleteValue();
			// 如果没有指定则使用全局配置的值
			if("".equals(deleteValue)) { 
				deleteValue = this.config.getLogicDeleteValue();
			}
			if("".equals(notDeleteValue)) {
				notDeleteValue = this.config.getLogicNotDeleteValue(); 
			}
			
			delVal = StringUtils.isNumeric(deleteValue) ? Integer.valueOf(deleteValue) : deleteValue;
			columnDefinition.setLogicDeleteValue(delVal);
			
			notDelVal = StringUtils.isNumeric(notDeleteValue) ? Integer.valueOf(notDeleteValue) : notDeleteValue;
			columnDefinition.setLogicNotDeleteValue(notDelVal);
		}
	}
	
	/** 是否是乐观锁字段 */
	private boolean isVersionColumn(Field field) {
		Version versionAnno = field.getAnnotation(Version.class);
		return versionAnno != null;
	}
	
	private void bindFill(ColumnDefinition columnDefinition,Field field) {
		String columnName = columnDefinition.getColumnName();
		BaseFill<?> fill = config.getFill(entityClass,field,columnName);
		if(fill != null) {
			columnDefinition.setTypeHandler(fill.getClass().getName());
			columnDefinition.setFillType(fill.getFillType());
		}
	}
	

}

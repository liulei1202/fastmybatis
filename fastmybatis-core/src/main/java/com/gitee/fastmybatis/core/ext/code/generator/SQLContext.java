package com.gitee.fastmybatis.core.ext.code.generator;

import java.util.List;

import com.gitee.fastmybatis.core.ext.code.util.FieldUtil;

/**
 * SQL上下文,这里可以取到表,字段信息<br>
 * 最终会把SQL上下文信息放到velocity中
 * @author tanghc
 */
public class SQLContext {
    /** 表结构定义 */
	private TableDefinition tableDefinition; 
	private String namespace;
	/** 包名 */
	private String packageName; 
	private String classSimpleName;
	/** java类完整路径,即:class.getName(); */
	private String className;

	public SQLContext(TableDefinition tableDefinition) {
		this.tableDefinition = tableDefinition;
	}

	public String getClassSimpleName() {
		return classSimpleName;
	}

	public void setClassSimpleName(String classSimpleName) {
		this.classSimpleName = classSimpleName;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * 返回Java类名
	 * 
	 * @return
	 */
	public String getJavaBeanName() {
		return classSimpleName;
	}

	/**
	 * 返回Java类名且首字母小写
	 * 
	 * @return
	 */
	public String getJavaBeanNameLF() {
		return FieldUtil.lowerFirstLetter(this.getJavaBeanName());
	}

	public TableDefinition getTableDefinition() {
		return tableDefinition;
	}

	public void setTableDefinition(TableDefinition tableDefinition) {
		this.tableDefinition = tableDefinition;
	}

	public List<ColumnDefinition> getColumnDefinitionList() {
		return tableDefinition.getColumnDefinitions();
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}

package com.gitee.fastmybatis.core.ext.code.generator;

import javax.persistence.Table;

import com.gitee.fastmybatis.core.FastmybatisConfig;
import com.gitee.fastmybatis.core.ext.code.util.FieldUtil;

/**
 * 表选择
 * @author tanghc
 */
public class TableSelector {

	private ColumnSelector columnSelector;
	private Class<?> entityClass;
	private FastmybatisConfig config;

	public TableSelector(Class<?> entityClass,FastmybatisConfig config) {
		if(config == null) {
			throw new IllegalArgumentException("FastmybatisConfig不能为null");
		}
		if(entityClass == null) {
			throw new IllegalArgumentException("entityClass不能为null");
		}
		this.entityClass = entityClass;
		this.config = config;
		this.columnSelector = new ColumnSelector(entityClass,config);
	}
	
	public TableDefinition getTableDefinition() {
		TableDefinition tableDefinition = new TableDefinition();
		Table tableAnno = entityClass.getAnnotation(Table.class);
		
		String schema = "";
		String tableName = entityClass.getSimpleName();
		
		if(tableAnno != null) {
			schema = tableAnno.schema();
			tableName = tableAnno.name();
			
		}else {
			String javaBeanName = entityClass.getSimpleName();
			if(config.isCamel2underline()) {
				tableName = FieldUtil.camelToUnderline(javaBeanName);
			}
		}
		
		tableDefinition.setSchema(schema);
		tableDefinition.setTableName(tableName);
		
		tableDefinition.setColumnDefinitions(columnSelector.getColumnDefinitions());
		
		return tableDefinition;
	}



}

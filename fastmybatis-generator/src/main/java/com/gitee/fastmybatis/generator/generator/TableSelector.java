package com.gitee.fastmybatis.generator.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gitee.fastmybatis.generator.entity.DataBaseConfig;
import com.gitee.fastmybatis.generator.util.SqlHelper;

public abstract class TableSelector {

	private ColumnSelector columnSelector;
	private DataBaseConfig dataBaseConfig;
	private List<String> schTableNames;

	public TableSelector(ColumnSelector columnSelector,DataBaseConfig dataBaseConfig) {
		this.dataBaseConfig = dataBaseConfig;
		this.columnSelector = columnSelector;
	}

	public abstract String getAllTablesSQL(String dbName);
	
	/**
	 * 查询数据库表的SQL
	 * 
	 * @return
	 */
	protected abstract String getShowTablesSQL(String dbName);
	
	protected abstract TableDefinition buildTableDefinition(Map<String, Object> tableMap);
	
	public List<TableDefinition> getAllTableDefinitions() {
		List<Map<String, Object>> resultList = SqlHelper.runSql(getDataBaseConfig(), getAllTablesSQL(dataBaseConfig.getDbName()));
		List<TableDefinition> tablesList = new ArrayList<TableDefinition>(resultList.size());
		
		for (Map<String, Object> rowMap : resultList) {
			TableDefinition tableDefinition = this.buildTableDefinition(rowMap);
			tableDefinition.setDataBaseConfig(dataBaseConfig);
			tableDefinition.setColumnDefinitions(columnSelector.getColumnDefinitions(tableDefinition.getTableName()));
			tablesList.add(tableDefinition);
		}
		
		return tablesList;
	}
	
	public List<TableDefinition> getTableDefinitions() {
		List<Map<String, Object>> resultList = SqlHelper.runSql(getDataBaseConfig(), getShowTablesSQL(dataBaseConfig.getDbName()));
		List<TableDefinition> tablesList = new ArrayList<TableDefinition>(resultList.size());
		
		for (Map<String, Object> rowMap : resultList) {
			TableDefinition tableDefinition = this.buildTableDefinition(rowMap);
			tableDefinition.setDataBaseConfig(dataBaseConfig);
			tableDefinition.setColumnDefinitions(columnSelector.getColumnDefinitions(tableDefinition.getTableName()));
			tableDefinition.setUuid(dataBaseConfig.isUuid());
			tablesList.add(tableDefinition);
		}
		
		if(tablesList.isEmpty()) {
			throw new RuntimeException("查不到表结构数据，检查配置文件是否正确");
		}
		
		return tablesList;
	}
	
	public List<TableDefinition> getSimpleTableDefinitions() {
		List<Map<String, Object>> resultList = SqlHelper.runSql(getDataBaseConfig(), getShowTablesSQL(dataBaseConfig.getDbName()));
		List<TableDefinition> tablesList = new ArrayList<TableDefinition>(resultList.size());
		
		for (Map<String, Object> rowMap : resultList) {
			tablesList.add(this.buildTableDefinition(rowMap));
		}
		
		return tablesList;
	}
	
	public DataBaseConfig getDataBaseConfig() {
		return dataBaseConfig;
	}

	public void setDataBaseConfig(DataBaseConfig dataBaseConfig) {
		this.dataBaseConfig = dataBaseConfig;
	}

	public ColumnSelector getColumnSelector() {
		return columnSelector;
	}

	public void setColumnSelector(ColumnSelector columnSelector) {
		this.columnSelector = columnSelector;
	}

	public List<String> getSchTableNames() {
		return schTableNames;
	}

	public void setSchTableNames(List<String> schTableNames) {
		this.schTableNames = schTableNames;
	}

}

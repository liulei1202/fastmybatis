package com.gitee.fastmybatis.generator.generator.sqlserver;

import java.util.Map;

import com.gitee.fastmybatis.generator.entity.DataBaseConfig;
import com.gitee.fastmybatis.generator.generator.ColumnSelector;
import com.gitee.fastmybatis.generator.generator.TableDefinition;
import com.gitee.fastmybatis.generator.generator.TableSelector;

public class SqlServerTableSelector extends TableSelector {
	
	public SqlServerTableSelector(ColumnSelector columnSelector,
			DataBaseConfig dataBaseConfig) {
		super(columnSelector, dataBaseConfig);
	}
	
	

	@Override
	public String getAllTablesSQL(String dbName) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	protected String getShowTablesSQL(String dbName) {
		StringBuilder sb = new StringBuilder();
		sb
		.append("SELECT ")
		.append("SS.name + '.' + t.name AS table_name ")
		.append(",ISNULL(ext.value, '') as comment ")
		.append("FROM sysobjects t ")
		.append("INNER JOIN sys.objects SO ON t.name = SO.name ")
		.append("INNER JOIN sys.schemas  SS ON SO.schema_id = SS.schema_id ")
		.append("LEFT JOIN sys.extended_properties ext ON ext.major_id = SO.object_id and ext.minor_id=0 ")
		.append("WHERE t.xtype='u' ");
		
		sb.append(this.buildTableSchWhere());
		
		sb.append(" ORDER BY SS.name ASC,t.name ASC");
		
		return sb.toString();
	}
	
	// and ( (t.name = 'bar' and SS.name = 'front') or (t.name = 'adjustBatch' and SS.name = 'account') )
	private String buildTableSchWhere() {
		if(this.getSchTableNames() != null && this.getSchTableNames().size() > 0) {
			int i = 0;
			StringBuilder tables = new StringBuilder(" and ( ");
			for (String table : this.getSchTableNames()) {
				String[] tableArr = table.split("\\.");
				if(i > 0) {
					tables.append(" or ");
				}
				if(tableArr.length == 1) {
					tables.append("(1=1 ").append(" and t.name='").append(tableArr[0]).append("') ");
				}else{
					tables.append("(SS.name='").append(tableArr[0]).append("' and t.name='").append(tableArr[1]).append("') ");
				}
				i++;
			}
			tables.append(" )");
			return tables.toString();
		}
		return "";
	}

	// {TABLE_NAME=account.adjustBatch, COMMENT=}
	@Override
	protected TableDefinition buildTableDefinition(Map<String, Object> tableMap) {
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName((String)tableMap.get("TABLE_NAME"));
		tableDefinition.setComment((String)tableMap.get("COMMENT"));
		return tableDefinition;
	}

}

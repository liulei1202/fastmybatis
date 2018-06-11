package com.gitee.fastmybatis.generator.generator.oracle;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import com.gitee.fastmybatis.generator.entity.DataBaseConfig;
import com.gitee.fastmybatis.generator.generator.ColumnDefinition;
import com.gitee.fastmybatis.generator.generator.ColumnSelector;

public class OracleColumnSelector extends ColumnSelector {

	public OracleColumnSelector(DataBaseConfig dataBaseConfig) {
		super(dataBaseConfig);
	}
	
	private static String sql = 
		"SELECT  "+
		"d.TABLE_NAME tbName,  "+
		"COALESCE(t.COMMENTS, '') tbDesc,  "+
		"a.COLUMN_NAME column_Name,  "+
		"a.DATA_TYPE column_Type,   "+
		"a.DATA_LENGTH width,   "+
		"a.DATA_SCALE precision,  "+
		"decode(a.NULLABLE,'Y','0','1') notNull,  "+
		"COALESCE(m.COMMENTS, '') comments,   "+
		"decode(k.uniqueness,'UNIQUE','1','0') uniques,   "+
		"COALESCE(k.index_name, ' ') indexName,  "+
		"decode(k.key,'Y','1','0') masterKey  "+
		"FROM  "+
		"user_tab_columns a  "+
		"INNER JOIN  user_tables d on a.TABLE_NAME=d.TABLE_NAME  "+
		"LEFT JOIN user_tab_comments t ON t.TABLE_NAME=d.TABLE_NAME  "+
		"LEFT JOIN user_col_comments m ON m.COLUMN_NAME=a.COLUMN_NAME AND m.TABLE_NAME=d.TABLE_NAME  "+
		"LEFT JOIN  "+
		"(  "+
		"SELECT e.index_name,u.TABLE_NAME,u.COLUMN_NAME,e.uniqueness,decode(p.constraint_name,NULL,'N','Y') key  "+
		"from user_indexes e INNER JOIN user_ind_columns u ON e.index_name=u.index_name  "+
		"LEFT JOIN ( select constraint_name from user_constraints where constraint_type='P' ) p ON e.index_name=p.constraint_name  "+
		") k ON k.TABLE_NAME=a.TABLE_NAME and k.COLUMN_NAME=a.COLUMN_NAME  "+
		"where a.table_name='%s'  "+
		"ORDER BY tbName";
	
	@Override
	protected String getColumnInfoSQL(String tableName) {
		return String.format(sql, tableName);
	}

	@Override
	protected ColumnDefinition buildColumnDefinition(Map<String, Object> rowMap) {
		Set<String> columnSet = rowMap.keySet();
		
		for (String columnInfo : columnSet) {
			rowMap.put(columnInfo.toUpperCase(), rowMap.get(columnInfo));
		}
		
		ColumnDefinition columnDefinition = new ColumnDefinition();
		
		String columnName = (String)rowMap.get("COLUMN_NAME");
		String type = (String)rowMap.get("COLUMN_TYPE");
		
		String masterKey = (String)rowMap.get("MASTERKEY");
		boolean isPk = "1".equals(masterKey);
		boolean isNumber = type.toUpperCase().contains("NUMBER");
		
		// 如果是主键并且是数字类型，就认为他是自增字段
		boolean isIdentity = isPk && isNumber;
		
		type = numberType(type, rowMap);
		
		columnDefinition.setColumnName(columnName);
		columnDefinition.setIsIdentity(isIdentity);
		columnDefinition.setIsPk(isPk);
		columnDefinition.setType(type);
		columnDefinition.setComment((String)rowMap.get("COMMENTS"));
		
		return columnDefinition;
	}
	
	private String numberType(String type,Map<String, Object> rowMap) {
		if(type.contains("NUMBER")) {
			
			BigDecimal precision = (BigDecimal)rowMap.get("PRECISION");
			int p = precision == null ? 0 : precision.intValue();
			// 如果是小数
			if(p > 0) {
				return "decimal";
			}else {
				// 整数
				return "integer";
			}
		}
		return type;
	}

}

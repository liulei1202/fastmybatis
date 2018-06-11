package com.gitee.fastmybatis.generator.generator;

import java.util.Collections;
import java.util.List;

import com.gitee.fastmybatis.generator.entity.DataBaseConfig;
import com.gitee.fastmybatis.generator.util.FieldUtil;
import com.gitee.fastmybatis.generator.util.UUIDUtil;

/**
 * 数据库表定义,从这里可以获取表名,字段信息
 */
public class TableDefinition {
	private DataBaseConfig dataBaseConfig;
	private String tableName; // 表名
	private String comment; // 注释
	private boolean uuid;
	private long serialId = UUIDUtil.nextId();
	
	private List<ColumnDefinition> columnDefinitions = Collections.emptyList(); // 字段定义

	public TableDefinition() {
	}

	public TableDefinition(String tableName) {
		this.tableName = tableName;
	}
	
	public String getDbName() {
		return dataBaseConfig.getDbName();
	}
	
	/**
	 * 返回Java类名
	 * @return
	 */
	public String getJavaBeanName(){
		String tableName = getJavaBeanNameLF();
		return FieldUtil.upperFirstLetter(tableName) + getEntityStuffix();
	}
	
    public String getEntityStuffix() {
        String stuffix = (String) dataBaseConfig.getParam().get("entitySuffix");
        if (stuffix == null) {
            return "";
        }
        return stuffix;
    }
	
	/**
	 * 返回Java类名且首字母小写
	 * @return
	 */
	public String getJavaBeanNameLF(){
		String tableName = getTableName();
		tableName = FieldUtil.underlineFilter(tableName);
		// 去掉"."，sqlserver会返回schema.tableName形式
		boolean showSchema = dataBaseConfig.isShowSchema();
		if(showSchema) {
			tableName = FieldUtil.dotFilter(tableName);
		}else {
			// 不显示schema
			tableName = FieldUtil.removeBeforeDot(tableName);
		}
		return FieldUtil.lowerFirstLetter(tableName);
	}
	
	/**
	 * 是否含有时间字段
	 * @return
	 */
	public boolean getHasDateField() {
		List<ColumnDefinition> columns = getColumnDefinitions();
		for (ColumnDefinition definition : columns) {
			if("Date".equals(definition.getJavaType())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否含有BigDecimal字段
	 * @return
	 */
	public boolean getHasBigDecimalField() {
		List<ColumnDefinition> columns = getColumnDefinitions();
		for (ColumnDefinition definition : columns) {
			if("BigDecimal".equals(definition.getJavaType())) {
				return true;
			}
		}
		return false;
	}
	

	public ColumnDefinition getPkColumn() {
		for (ColumnDefinition column : columnDefinitions) {
			if (column.getIsPk()) {
				return column;
			}
		}
		return null;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<ColumnDefinition> getColumnDefinitions() {
		return columnDefinitions;
	}

	public void setColumnDefinitions(List<ColumnDefinition> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
	}

	public DataBaseConfig getDataBaseConfig() {
		return dataBaseConfig;
	}

	public void setDataBaseConfig(DataBaseConfig dataBaseConfig) {
		this.dataBaseConfig = dataBaseConfig;
	}

	public boolean isUuid() {
		return uuid;
	}

	public void setUuid(boolean uuid) {
		this.uuid = uuid;
	}

	public long getSerialId() {
		return serialId;
	}

	public void setSerialId(long serialId) {
		this.serialId = serialId;
	}
	
}

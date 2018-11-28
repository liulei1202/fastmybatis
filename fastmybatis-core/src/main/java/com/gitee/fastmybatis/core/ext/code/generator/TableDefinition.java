package com.gitee.fastmybatis.core.ext.code.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gitee.fastmybatis.core.ext.exception.GenCodeException;

/**
 * 数据库表定义,从这里可以获取表名,字段信息
 * 
 * @author tanghc
 */
public class TableDefinition {
    private String schema;
    /** 表名 */
    private String tableName;
    /** 注释 */
    private String comment;
    /** 字段定义 */
    private List<ColumnDefinition> columnDefinitions = Collections.emptyList();
    private ColumnDefinition pkColumn;
    /** 乐观锁字段 */
    private ColumnDefinition versionColumn;
    /** 逻辑删除字段 */
    private ColumnDefinition logicDeleteColumn;

    public TableDefinition() {
    }

    public TableDefinition(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 返回表字段
     * 
     * @return 返回表字段
     */
    public List<ColumnDefinition> getTableColumns() {
        List<ColumnDefinition> columns = this.getColumnDefinitions();

        List<ColumnDefinition> ret = new ArrayList<>();

        for (ColumnDefinition columnDefinition : columns) {
            if (!columnDefinition.isTransient()) {
                ret.add(columnDefinition);
            }
        }

        return ret;
    }

    /**
     * 返回所有定义的字段
     * 
     * @return 返回所有定义的字段
     */
    public List<ColumnDefinition> getAllColumns() {
        return this.getColumnDefinitions();
    }

    /**
     * 是否含有时间字段
     * 
     * @return true，含有时间字段
     */
    public boolean getHasDateField() {
        List<ColumnDefinition> columns = getColumnDefinitions();
        for (ColumnDefinition definition : columns) {
            if ("Date".equals(definition.getJavaType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否含有BigDecimal字段
     * 
     * @return true，含有BigDecimal字段
     */
    public boolean getHasBigDecimalField() {
        List<ColumnDefinition> columns = getColumnDefinitions();
        for (ColumnDefinition definition : columns) {
            if ("BigDecimal".equals(definition.getJavaType())) {
                return true;
            }
        }
        return false;
    }

    public boolean getHasVersionColumn() {
        return this.versionColumn != null;
    }

    public boolean getHasLogicDeleteColumn() {
        return this.logicDeleteColumn != null;
    }

    public ColumnDefinition getPkColumn() {
        return this.pkColumn;
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

        this.initPKColumn(columnDefinitions);
        this.initVersionJavaColumn(columnDefinitions);
        this.initDeleteJavaColumn(columnDefinitions);
        this.initOrderIndex(columnDefinitions);
    }
    
    private void initOrderIndex(List<ColumnDefinition> columnDefinitions) {
    	Collections.sort(columnDefinitions,new Comparator<ColumnDefinition>(){
			@Override
			public int compare(ColumnDefinition o1, ColumnDefinition o2) {
				return Integer.compare(o1.getOrderIndex(), o2.getOrderIndex());
			}
    	});
    }

    private void initPKColumn(List<ColumnDefinition> columnDefinitions) {
        for (ColumnDefinition column : columnDefinitions) {
            if (column.getIsPk()) {
            	column.setOrderIndex(0);
                this.setPkColumn(column);
                break;
            }
        }
    }

    private void initVersionJavaColumn(List<ColumnDefinition> columnDefinitions) {
        for (ColumnDefinition column : columnDefinitions) {
            if (column.getIsVersion()) {
                this.setVersionColumn(column);
                break;
            }
        }
    }

    private void initDeleteJavaColumn(List<ColumnDefinition> columnDefinitions) {
        int count = 0;
        for (ColumnDefinition column : columnDefinitions) {
            if (column.getIsLogicDelete()) {
                if (count == 1) {
                    throw new GenCodeException(
                            column.getJavaFieldName() + "字段重复定义@LogicDelete.确保实体类中只有一个@LogicDelete注解");
                }
                this.setLogicDeleteColumn(column);
                count++;
            }
        }
    }

    public ColumnDefinition getVersionColumn() {
        return versionColumn;
    }

    public void setVersionColumn(ColumnDefinition versionColumn) {
        this.versionColumn = versionColumn;
    }

    public void setPkColumn(ColumnDefinition pkColumn) {
        this.pkColumn = pkColumn;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public ColumnDefinition getLogicDeleteColumn() {
        return logicDeleteColumn;
    }

    public void setLogicDeleteColumn(ColumnDefinition logicDeleteColumn) {
        this.logicDeleteColumn = logicDeleteColumn;
    }

}

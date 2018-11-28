package com.gitee.fastmybatis.core.ext.code.generator;

import com.gitee.fastmybatis.core.ext.code.util.FieldUtil;
import com.gitee.fastmybatis.core.ext.code.util.JavaTypeUtil;
import com.gitee.fastmybatis.core.handler.FillType;

/**
 * 表字段信息
 * 
 * @author tanghc
 */
public class ColumnDefinition {

    private static final String PREFIX = "entity.";

    /** java字段名 */
    private String javaFieldName;
    /** 数据库字段名 */
    private String columnName;
    /** javaBean字段类型，String，Integer等 */
    private String type;
    /** javaBean字段完整类型，java.lang.String */
    private String fullType;
    private boolean isTransient;
    /** 是否自增 */
    private boolean isIdentity;
    /** 是否uuid策略 */
    private boolean isUuid;
    /** 是否主键 */
    private boolean isPk;
    private boolean isEnum;
    /** 是否乐观锁字段 */
    private boolean isVersion;
    /** 是否逻辑删除 */
    private boolean isLogicDelete;
    /** 删除值 */
    private Object logicDeleteValue;
    /** 未删除值 */
    private Object logicNotDeleteValue;
    private String comment;
    private String typeHandler;
    private FillType fillType;
    
    private int orderIndex = 1;

    public String getLogicDeleteValueString() {
        return formatValue(logicDeleteValue);
    }

    public String getLogicNotDeleteValueString() {
        return formatValue(logicNotDeleteValue);
    }

    public String getJdbcTypeProperty() {
        return typeHandler == null ? "jdbcType=\"" + this.getMybatisJdbcType() + "\"" : "";
    }

    public String getJavaTypeProperty() {
        return typeHandler != null ? "javaType=\"" + this.getFullType() + "\"" : "";
    }

    public String getTypeHandlerProperty() {
        return typeHandler != null ? " typeHandler=\"" + typeHandler + "\" " : "";
    }

    private String getTypeHandlerValue(FillType type) {
        return hasTypeHandler(type) ? (", typeHandler=" + typeHandler) : "";
    }

    public boolean getHasTypeHandlerInsert() {
        return this.hasTypeHandler(FillType.INSERT);
    }

    public boolean getHasTypeHandlerUpdate() {
        return this.hasTypeHandler(FillType.UPDATE);
    }

    private boolean hasTypeHandler(FillType type) {
        return typeHandler != null && FillType.checkPower(this.fillType, type);
    }

    public String getMybatisInsertValue() {
        return getMybatisValue(FillType.INSERT);
    }

    public String getMybatisInsertValuePrefix() {
        return getMybatisValue(FillType.INSERT, PREFIX);
    }

    public String getMybatisUpdateValue() {
        return getMybatisValue(FillType.UPDATE);
    }

    public String getMybatisUpdateValuePrefix() {
        return getMybatisValue(FillType.UPDATE, PREFIX);
    }

    public String getMybatisSelectValue() {
        return getMybatisValue(FillType.SELECT);
    }

    private String getMybatisValue(FillType fillType) {
        return this.getMybatisValue(fillType, "");
    }

    /**
     * 返回 mybatis值内容
     * @param fillType 填充类型
     * @param prefix 前缀
     * @return 如返回<code>#{userName}</code>
     */
    private String getMybatisValue(FillType fillType, String prefix) {
        // 如果是乐观锁字段
        if (this.isVersion) { 
            return this.columnName + "+1";
        } else {
            StringBuilder mybatisValue = new StringBuilder();
            mybatisValue.append("#{" + prefix + this.getJavaFieldName()).append(this.getTypeHandlerValue(fillType))
                    .append("}");

            return mybatisValue.toString(); 
        }
    }

    /**
     * 是否是乐观锁字段
     * 
     * @return true是
     */
    public boolean getIsVersion() {
        return this.isVersion;
    }

    public void setIsVersion(boolean isVersion) {
        this.isVersion = isVersion;
    }

    /**
     * 是否是自增主键
     * 
     * @return true，是
     */
    public boolean getIsIdentityPk() {
        return isPk && isIdentity;
    }

    /**
     * 返回java字段名,并且第一个字母大写
     * 
     * @return 返回java字段名,并且第一个字母大写
     */
    public String getJavaFieldNameUF() {
        return FieldUtil.upperFirstLetter(getJavaFieldName());
    }

    /**
     * 返回java字段
     * 
     * @return 返回java字段
     */
    public String getJavaFieldName() {
        return javaFieldName;
    }

    public String getJavaType() {
        return type;
    }

    /**
     * 获得装箱类型,Integer,Float
     * 
     * @return 获得装箱类型,Integer,Float
     */
    public String getJavaTypeBox() {
        return JavaTypeUtil.convertToJavaBoxType(type);
    }

    public String getMybatisJdbcType() {
        return JavaTypeUtil.convertToMyBatisJdbcType(type);
    }

    public void setJavaFieldName(String javaFieldName) {
        this.javaFieldName = javaFieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsIdentity() {
        return isIdentity;
    }

    public void setIsIdentity(boolean isIdentity) {
        this.isIdentity = isIdentity;
    }

    public boolean getIsPk() {
        return isPk;
    }

    public void setIsPk(boolean isPk) {
        this.isPk = isPk;
    }

    public boolean getIsUuid() {
        return isUuid;
    }

    public void setIsUuid(boolean isUuid) {
        this.isUuid = isUuid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isTransient() {
        return isTransient;
    }

    public void setTransient(boolean isTransient) {
        this.isTransient = isTransient;
    }

    public boolean getIsEnum() {
        return isEnum;
    }

    public void setEnum(boolean isEnum) {
        this.isEnum = isEnum;
    }

    public String getFullType() {
        return fullType;
    }

    public void setFullType(String fullType) {
        this.fullType = fullType;
    }

    public void setFillType(FillType fillType) {
        this.fillType = fillType;
    }

    public void setTypeHandler(String typeHandler) {
        this.typeHandler = typeHandler;
    }

    public void setIsLogicDelete(boolean isLogicDelete) {
        this.isLogicDelete = isLogicDelete;
    }

    public boolean getIsLogicDelete() {
        return this.isLogicDelete;
    }

    public Object getLogicDeleteValue() {
        return logicDeleteValue;
    }

    public void setLogicDeleteValue(Object logicDeleteValue) {
        this.logicDeleteValue = logicDeleteValue;
    }

    public Object getLogicNotDeleteValue() {
        return logicNotDeleteValue;
    }

    public void setLogicNotDeleteValue(Object logicNotDeleteValue) {
        this.logicNotDeleteValue = logicNotDeleteValue;
    }

    private String formatValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        } else {
            return String.valueOf(value);
        }
    }

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}
    
}

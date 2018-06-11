package com.gitee.fastmybatis.generator.generator;

import java.util.Map;

import com.gitee.fastmybatis.generator.util.FieldUtil;
import com.gitee.fastmybatis.generator.util.SqlTypeUtil;

/**
 * 表字段信息
 */
public class ColumnDefinition  {

	private String columnName; // 数据库字段名
	private String type; // 数据库类型
	private boolean isIdentity; // 是否自增
	private boolean isPk; // 是否主键
	private String comment; // 字段注释
	private Map<Object, Object> param;

	/**
	 * 是否是自增主键
	 * 
	 * @return
	 */
	public boolean getIsIdentityPk() {
		return isPk && isIdentity;
	}
	
	/**
	 * 返回java字段名,并且第一个字母大写
	 * 
	 * @return
	 */
	public String getJavaFieldNameUF() {
		return FieldUtil.upperFirstLetter(getJavaFieldName());
	}
	
	/**
	 * 返回java字段。规则：
	 * 1. 如果字母全部大写并且用下划线连接，则全部把字母转换成小写，然后去掉下划线改驼峰形式。（如oracle字段）<br>
	 * 2. 如果字母全部是大写且没有下划线，则全部把字母转换成小写。<br>
	 * 3. 如果字母全部小写并且用下划线连接，去掉下划线改驼峰形式。（如mysql字段）<br>
	 * 3. 其它情况直接返回原字符串。
	 * @return
	 */
	public String getJavaFieldName() {
		String column = this.columnName;
		
		if(this.getUseDbColumn()) {
            return column;
        }
		
		if(isAllUpperCaseLettersWith_(column)) {
			column = column.toLowerCase();
			column = FieldUtil.underlineFilter(column);
		}else if(isAllUpperCaseLetters(column)) {
			column = column.toLowerCase();
		}else if(isAllLowerCaseLettersWith_(column)) {
			column = FieldUtil.underlineFilter(column);
		}
		
		return column;
	}
	
	private static boolean isAllUpperCaseLettersWith_(String columnName) {
		boolean has_ = columnName.contains("_");
		
		return has_ && isAllUpperCaseLetters(columnName);
	}
	
	private static boolean isAllUpperCaseLetters(String columnName) {
		
		boolean isAllUpperCase = columnName.replaceAll("[^a-zA-Z]", "").matches("^[A-Z]+$");
		
		return isAllUpperCase;
	}
	
	private static boolean isAllLowerCaseLettersWith_(String columnName) {
		boolean has_ = columnName.contains("_");
		
		return has_ && isAllLowerCaseLetters(columnName);
	}
	
	private static boolean isAllLowerCaseLetters(String columnName) {
		
		boolean isAllUpperCase = columnName.replaceAll("[^a-zA-Z]", "").matches("^[a-z]+$");
		
		return isAllUpperCase;
	}
	
	/**
	 * 获得基本类型,int,float
	 * @return
	 */
	
	public String getJavaType() {
		String typeLower = type.toLowerCase();
		return SqlTypeUtil.convertToJavaType(typeLower);
	}
	
	/**
	 * 获得装箱类型,Integer,Float
	 * @return
	 */
	
	public String getJavaTypeBox(){
		String typeLower = type.toLowerCase();
		return SqlTypeUtil.convertToJavaBoxType(typeLower);
	}
	
	public String getMybatisJdbcType() {
		String typeLower = type.toLowerCase();
		return SqlTypeUtil.convertToMyBatisJdbcType(typeLower);
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

    public boolean getUseDbColumn() {
        return "true".equals(getParam().get("useDbColumn"));
    }

    public Map<Object, Object> getParam() {
        return param;
    }

    public void setParam(Map<Object, Object> param) {
        this.param = param;
    }

}

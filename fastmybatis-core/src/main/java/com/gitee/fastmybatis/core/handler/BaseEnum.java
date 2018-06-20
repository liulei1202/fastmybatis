package com.gitee.fastmybatis.core.handler;

/**
 * 字段枚举类型父类.
 * 如果javaBean字段要实现枚举类型，枚举类必须实现BaseEnum。如：
 * <pre>
 * public enum UserInfoType implements BaseEnum&lt;String&gt;{
	INVALID("0"),VALID("1")
	;

	private String status;

	UserInfoType(String type) {
		this.status = type;
	}
	
	public String getCode() {
		return status;
	}

}
 * </pre>
 * 
 * 
 * @author tanghc
 *
 * @param <T> 枚举值类型，通常是Integer或String
 */
public interface BaseEnum<T> {
	/**
	 * 返回code
	 * @return code值
	 */
	T getCode();
}
package com.gitee.fastmybatis.core.query;

/**
 * 排序支持
 * @author tanghc
 */
public interface Sortable {
	/**
	 * 是否具备排序,是返回true
	 * @return true，具备排序
	 */
	boolean getOrderable();
	
	/**
	 * 返回排序信息
	 * @return 返回排序信息,如:id ASC,name ASC,date desc. 没有排序则返回""
	 */
	String getOrder();
}

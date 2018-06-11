package com.myapp.common.bean;

/**
 * 通用返回结果
 */
public class ResultBean extends ResultSupport<Integer, Object> {

	public String getMsg() {
		return this.fatchMsg();
	}

	public Object getData() {
		return this.fatchData();
	}

	public Integer getCode() {
		return this.fatchCode();
	}

}

package com.myapp.common.bean;

/**
 * 返回结果
 */
public interface Result<C, D> {
	void setCode(C code);

	void setMsg(String msg);

	void setData(D data);
}

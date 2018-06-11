package com.myapp.common.msg;

import com.myapp.common.exception.SysException;

/**
 * 错误对象
 * 
 * @author tanghc
 *
 */
public class ErrorMeta implements MsgInfo<Integer> {

	public ErrorMeta(Integer code, String msg) {
		super();
		this.msg = msg;
		this.code = code;
	}

	private String msg;
	private Integer code;

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	public RuntimeException getException() {
		return new SysException(this);
	}
	
	public RuntimeException getException(Object data) {
		return new SysException(this, data);
	}

}

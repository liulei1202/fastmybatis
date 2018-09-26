package com.gitee.fastmybatis.core.ext.exception;

/**
 * 生成mapper代码异常
 * 
 * @author tanghc
 *
 */
@SuppressWarnings("serial")
public class GenCodeException extends RuntimeException {
	public GenCodeException(Throwable cause) {
		super(cause);
	}

	public GenCodeException(String message) {
		super(message);
	}
	
}

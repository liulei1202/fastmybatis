package com.gitee.fastmybatis.core.ext.exception;

/**
 * @author tanghc
 */
@SuppressWarnings("serial")
public class DatabaseConnectException extends RuntimeException {
	public DatabaseConnectException(Throwable cause) {
		super(cause);
	}
}

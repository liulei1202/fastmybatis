package com.myapp;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myapp.common.bean.ResultBean;
import com.myapp.common.bean.Results;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResultBean jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		return Results.error(e);
	}

}

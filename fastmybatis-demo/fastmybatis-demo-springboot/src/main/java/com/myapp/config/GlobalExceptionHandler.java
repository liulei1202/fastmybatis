package com.myapp.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResultBean jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
	    logger.error(e.getMessage(),e);
		return Results.error(e);
	}

}

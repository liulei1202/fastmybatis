package com.myapp.common.bean;

import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.myapp.common.util.RequestUtil;
import com.myapp.common.util.ValidateUtil;

/**
 * 提供基础能力的Controller,如果一个Controller具备简单功能可以继承这个类
 * 
 * @author tanghc 2015-2-28
 */
public abstract class BaseController {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private ConcurrentMap<String,PropertyEditor> propertyEditorStore = new ConcurrentHashMap<>();

	
	/**
	 * 获取HttpServletRequest
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 获取HttpServletResponse
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	/**
	 * 获取httpSession
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * 获取客户端真实IP
	 * @return
	 */
	public String getClientIP() {
		return RequestUtil.getClientIP(getRequest());
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		this.initCustomEditor(binder);
	}

	protected void initCustomEditor(WebDataBinder binder) {
		Class<?> clazz = this.getClass();
		String key = clazz.getName();
		// 默认时间转换
		PropertyEditor propertyEditor = propertyEditorStore.get(key);
		if (propertyEditor == null) {
			DateFormat dateFormat = new SimpleDateFormat(getDateFormatPattern());
			propertyEditor = new CustomDateEditor(dateFormat, true);
			propertyEditorStore.put(key,propertyEditor);
		}

		binder.registerCustomEditor(Date.class, propertyEditor);
	}

	protected String getDateFormatPattern() {
		return "yyyy-MM-dd";
	}

	public Logger getLogger() {
		return logger;
	}

	public ModelAndView newModelView(String viewName) {
		return new ModelAndView(viewName);
	}

	public ModelAndView newModelView(String viewName, Map<String, ?> model) {
		return new ModelAndView(viewName, model);
	}

	public ModelAndView newModelView(String viewName, String modelName, Object modelObject) {
		return new ModelAndView(viewName, modelName, modelObject);
	}
	
	public ValidateHolder validate(Object entity) {
		return ValidateUtil.validate(entity);
	}
	
	public void check(Object entity) {
		ValidateHolder ret = this.validate(entity);
		if(!ret.isSuccess()) {
			throw ret.getException();
		}
	}

}

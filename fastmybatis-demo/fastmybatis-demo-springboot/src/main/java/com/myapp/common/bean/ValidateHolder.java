package com.myapp.common.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.myapp.common.exception.SysException;
import com.myapp.common.msg.Errors;

/**
 * 验证结果
 * @author tanghc
 * 2014年6月19日
 * @param <T>
 *
 */
public class ValidateHolder {
	private boolean isSuccess;
	private Set<ConstraintViolation<Object>> constraintViolations;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Set<ConstraintViolation<Object>> buildConstraintViolations() {
		return constraintViolations;
	}

	public void setConstraintViolations(
			Set<ConstraintViolation<Object>> constraintViolations) {
		this.constraintViolations = constraintViolations;
	}
	
	
	/**
	 * 返回错误消息,多个错误消息用","隔开
	 * @author tanghc
	 * @return
	 */
	public String getErrorMsg() {
		return this.getErrorMsg(null);
	}
	
	/**
	 * 返回错误信息,多条错误使用splitChar隔开
	 * @param splitChar 如果为null,则使用","
	 * @return
	 */
	public String getErrorMsg(String splitChar) {
		if(splitChar == null) {
			splitChar = ",";
		}
		List<String> errors = this.getErrors();
		if(errors.size() == 0) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (String error : errors) {
			sb.append(splitChar).append(error);
		}
		
		String errorMsg = sb.toString();
		
		return errorMsg.substring(1);
		
	}
	
	/**
	 * 返回错误消息
	 * @author tanghc
	 * @return
	 */
	public List<String> getErrors() {
		return this.buildValidateErrors();
	}
	
	public SysException getException() {
		Object data = this.getFormError();
		return new SysException(Errors.ERROR_VALIDATE, data);
	}
	
	/**
	 * 返回表单错误,Map中的key为表单中的name,value为错误信息.
	 * @author tanghc
	 * @return
	 */
	public Map<String, String> getFormError() {
		Set<ConstraintViolation<Object>> set = this.buildConstraintViolations();
		Map<String, String> error = new HashMap<String, String>(set.size());
		
		for (ConstraintViolation<Object> c : set) {
			error.put(c.getPropertyPath().toString(),c.getMessage());
		}
		
		return error;
	}
	
	// 返回格式类似于:["用户名错误","密码不正确"]
	private List<String> buildValidateErrors() {
		Set<ConstraintViolation<Object>> set = this.buildConstraintViolations();
		List<String> errors = new ArrayList<String>();
		
		for (ConstraintViolation<Object> c : set) {
			errors.add(c.getMessage());
		}
		
		return errors;
	}
	
	
	
	
	
}

package com.myapp.common.util;

import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.myapp.common.bean.ValidateHolder;

/**
 * 验证工具类 JSR303
 * 
 * @author tanghc 2014年6月5日
 * 
 */
public class ValidateUtil {

	private static final ValidateHolder SUCCESS_HOLDER = new ValidateHolder();

	private static ValidatorFactory factory = null;
    private static Validator validator;
	static {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		
		SUCCESS_HOLDER.setSuccess(true);
		SUCCESS_HOLDER.setConstraintViolations(Collections
				.<ConstraintViolation<Object>> emptySet());
	}

	/**
	 * 构建验证器
	 * @return
	 */
	public static Validator getValidator() {
		return validator;
	}

	/**
	 * 验证
	 * 
	 * @param obj
	 *            属性使用JSR303注解
	 * @return
	 */
	public static ValidateHolder validate(Object obj) {

		ValidateHolder holder = new ValidateHolder();

		Set<ConstraintViolation<Object>> set = getValidator().validate(obj);

		holder.setConstraintViolations(set);
		holder.setSuccess(set.size() == 0);

		return holder;

	}

}

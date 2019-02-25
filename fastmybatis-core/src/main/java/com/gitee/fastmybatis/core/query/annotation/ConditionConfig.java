package com.gitee.fastmybatis.core.query.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 条件表达式,作用在bean上
 *
 * @author tanghc
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ConditionConfig {

    /**
     * @return 类中的字段是否驼峰转下划线形式
     */
    boolean camel2underline() default true;

    /**
     * @return 忽略的字段，填类字段名
     */
    String[] ignoreFields() default {};
}

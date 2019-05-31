package com.gitee.fastmybatis.core.handler;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.lang.reflect.Field;

/**
 * 自定义ID填充器，实现自定义ID生成策略，需要继承这个类。
 *
 * @author tanghc
 */
public abstract class CustomIdFill<T> extends BaseFill<T> {

    @Override
    public boolean match(Class<?> entityClass, Field field, String columnName) {
        // 是否有@Id注解
        boolean isPk = field.getAnnotation(Id.class) != null;
        GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        // 是否有@GeneratedValue注解，并且策略是AUTO
        boolean isAuto = (generatedValue == null || generatedValue.strategy() == GenerationType.AUTO);
        return isPk && isAuto;
    }

    @Override
    public FillType getFillType() {
        return FillType.INSERT; // INSERT时触发
    }

    @Override
    protected Object buildFillValue(T parameter) {
        Object val = super.buildFillValue(parameter);
        Identitys.set(val);
        return val;
    }
}

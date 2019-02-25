package com.gitee.fastmybatis.core.query;

/**
 * 条件值处理
 * @author tanghc
 */
public interface ConditionValueHandler {

    /**
     * 获取条件的值
     * @param defaultValue 默认值
     * @param fieldName 字段名称
     * @param target 字段所在的类
     * @return 返回真实值
     */
    Object getConditionValue(Object defaultValue, String fieldName, Object target);

    /** 默认的处理 */
    class DefaultConditionValueHandler implements ConditionValueHandler {
        @Override
        public Object getConditionValue(Object defaultValue, String fieldName, Object target) {
            return defaultValue;
        }
    }
}

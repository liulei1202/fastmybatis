package com.gitee.fastmybatis.core.query.expression.builder;

import com.gitee.fastmybatis.core.exception.QueryException;
import com.gitee.fastmybatis.core.ext.code.util.FieldUtil;
import com.gitee.fastmybatis.core.query.ConditionValueHandler;
import com.gitee.fastmybatis.core.query.annotation.Condition;
import com.gitee.fastmybatis.core.query.annotation.ConditionConfig;
import com.gitee.fastmybatis.core.query.expression.Expression;
import com.gitee.fastmybatis.core.query.expression.Expressions;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 条件生成
 *
 * @author tanghc
 */
public class ConditionBuilder {
    private static final Log LOG = LogFactory.getLog(ConditionBuilder.class);

    private static final String PREFIX_GET = "get";
    private static final String GETCLASS_NAME = "getClass";

    private static ConditionBuilder underlineFieldBuilder = new ConditionBuilder(true);
    private static ConditionBuilder camelFieldBuilder = new ConditionBuilder(false);

    private static Map<String, String> fieldToColumnNameMap = new HashMap<>(16);
    private static Map<String, ConditionValueHandler> conditionValueHandlerMap = new HashMap<>(16);
    private static Map<String, Condition> methodConditionCache = new HashMap<>(16);

    private boolean camel2underline = Boolean.TRUE;

    public ConditionBuilder() {
    }

    public ConditionBuilder(boolean camel2underline) {
        this.camel2underline = camel2underline;
    }

    public static ConditionBuilder getUnderlineFieldBuilder() {
        return underlineFieldBuilder;
    }

    public static ConditionBuilder getCamelFieldBuilder() {
        return camelFieldBuilder;
    }


    /**
     * 获取条件表达式
     *
     * @param pojo pojo对象
     * @return 返回表达式结合
     */
    public List<Expression> buildExpressions(Object pojo) {
        Assert.notNull(pojo, "buildExpressions(Object pojo) pojo can't be null.");
        List<Expression> expList = new ArrayList<Expression>();
        Class<?> clazz = pojo.getClass();
        ConditionConfig conditionConfig = clazz.getAnnotation(ConditionConfig.class);
        String[] ignoreFields = conditionConfig == null ? null : conditionConfig.ignoreFields();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            try {
                if (couldBuildExpression(method)) {
                    // 类字段名
                    String fieldName = this.buildFieldName(method);
                    if (ignoreFields != null && ArrayUtils.contains(ignoreFields, fieldName)) {
                        continue;
                    }
                    Condition condition = this.findCondition(method, fieldName);
                    Object value = this.getMethodValue(method, fieldName, condition, pojo);
                    if (value == null) {
                        continue;
                    }
                    // 数据库字段名
                    String columnName = this.getColumnName(method, condition, conditionConfig);
                    Expression expression = buildExpression(condition, columnName, value);
                    if (expression != null) {
                        expList.add(expression);
                    }
                }
            } catch (Exception e) {
                LOG.error("构建表达式失败，menthod:" + method, e);
                throw new QueryException(e);
            }
        }
        return expList;
    }

    private Expression buildExpression(Condition condition, String columnName, Object value) {
        Expression expression;
        if (condition == null) {
            if (value.getClass().isArray()) {
                expression = Expressions.in(columnName, (Object[]) value);
            } else if (value instanceof Collection) {
                expression = Expressions.in(columnName, (Collection<?>) value);
            } else {
                expression = Expressions.eq(columnName, value);
            }
        } else {
            expression = ExpressionBuilder.buildExpression(condition, columnName, value);
        }
        return expression;
    }

    private Condition findCondition(Method method, String fieldName) {
        String key = method.toString();
        Condition annotation = methodConditionCache.get(key);
        if (annotation == null) {
            // 先找get方法上的注解
            annotation = method.getAnnotation(Condition.class);
            if (annotation == null) {
                // 找不到再找字段上的注解
                Class<?> clazz = method.getDeclaringClass();
                Field field = ReflectionUtils.findField(clazz, fieldName);
                if (field != null) {
                    annotation = field.getAnnotation(Condition.class);
                }
                if (annotation != null) {
                    methodConditionCache.put(key, annotation);
                }
            }
        }
        return annotation;
    }

    private String getColumnName(Method method, Condition condition, ConditionConfig conditionConfig) {
        String key = method.toString();
        String columnName = fieldToColumnNameMap.get(key);
        if (columnName != null) {
            return columnName;
        }
        columnName = buildColumnNameByCondition(condition);
        if (columnName == null || "".equals(columnName)) {
            columnName = this.buildColumnNameByMethod(method, conditionConfig);
        }
        fieldToColumnNameMap.put(key, columnName);
        return columnName;
    }

    private String buildColumnNameByMethod(Method method, ConditionConfig conditionConfig) {
        String columnName = this.buildColumnName(method);
        boolean camel2underline = conditionConfig == null ? this.camel2underline : conditionConfig.camel2underline();
        if (camel2underline) {
            columnName = FieldUtil.camelToUnderline(columnName);
        }
        return columnName;
    }

    private String buildColumnNameByCondition(Condition condition) {
        String columnName = null;
        if (condition != null) {
            String column = condition.column();
            // 如果注解里面直接申明了字段名，则返回
            if (!"".equals(column)) {
                columnName = column;
            }
        }
        return columnName;
    }

    private Object getMethodValue(Method method, String fieldName, Condition condition, Object pojo) throws InvocationTargetException, IllegalAccessException {
        Object fieldValue = method.invoke(pojo);
        Class<? extends ConditionValueHandler> handlerClass = condition == null ? null : condition.handlerClass();
        if (handlerClass != null && handlerClass != ConditionValueHandler.DefaultConditionValueHandler.class) {
            try {
                ConditionValueHandler conditionValueHandler = this.getValueHandler(handlerClass);
                // 格式化返回内容，做一些特殊处理
                fieldValue = conditionValueHandler.getConditionValue(fieldValue, fieldName, pojo);
            } catch (Exception e) {
                LOG.error("handlerClass.newInstance出错，class:" + handlerClass.getName(), e);
                throw new QueryException("实例化ConditionValueHandler出错，field:" + method);
            }
        }
        return fieldValue;
    }

    private ConditionValueHandler getValueHandler(Class<? extends ConditionValueHandler> handlerClass) throws IllegalAccessException, InstantiationException {
        String key = handlerClass.getName();
        ConditionValueHandler conditionValueHandler = conditionValueHandlerMap.get(key);
        if (conditionValueHandler == null) {
            conditionValueHandler = handlerClass.newInstance();
            conditionValueHandlerMap.put(key, conditionValueHandler);
        }
        return conditionValueHandler;
    }

    /**
     * 返回数据库字段名
     */
    private String buildColumnName(Method method) {
        String getMethodName = method.getName();
        String columnName = getMethodName.substring(3);
        columnName = FieldUtil.lowerFirstLetter(columnName);
        return columnName;
    }

    /**
     * 返回字段名
     */
    private String buildFieldName(Method method) {
        // getUsername()
        String getMethodName = method.getName();
        // Username
        String columnName = getMethodName.substring(3);
        // username
        columnName = FieldUtil.lowerFirstLetter(columnName);
        return columnName;
    }

    /**
     * 能否构建表达式
     */
    private static boolean couldBuildExpression(Method method) {
        if (method.getReturnType() == Void.TYPE) {
            return false;
        }
        String methodName = method.getName();
        return (!GETCLASS_NAME.equals(methodName)) && methodName.startsWith(PREFIX_GET);
    }

}

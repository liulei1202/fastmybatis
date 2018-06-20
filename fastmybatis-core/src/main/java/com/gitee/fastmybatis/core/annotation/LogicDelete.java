package com.gitee.fastmybatis.core.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 逻辑删除注解.<br>
 * 作用在字段上进行逻辑删除,当调用dao.del(obj)时,实际触发update语句<br>
 * 
 * <pre>
 * <code>
public class User {
    {@literal @LogicDelete}
    private Byte isDeleted;
}
</code>
也可以指定保存的值
<code>
public class Address {
    {@literal @LogicDelete(deleteValue = "t", notDeleteValue = "f")}
    private String isdel;
}
</code>
 * </pre>
 * 
 * @author tanghc
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface LogicDelete {

    /**
     * 未删除数据库保存的值,不指定默认为0
     * 
     * @return 未删除数据库保存的值
     */
    String notDeleteValue() default "";

    /**
     * 删除后数据库保存的值,不指定默认为1
     * 
     * @return 删除后数据库保存的值
     */
    String deleteValue() default "";

}

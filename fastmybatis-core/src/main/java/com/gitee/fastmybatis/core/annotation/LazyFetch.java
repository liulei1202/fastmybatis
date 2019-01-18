package com.gitee.fastmybatis.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * 懒加载配置，作用在实体类的字段上。<br>
 * <pre><code>
 * public class UserInfo {
 *
 *     // 一对一配置，这里的user_id对应TUser主键
 *  	// 触发懒加载时，会拿user_id的值去查询t_user表
 *  	// 即：SELECT * FROM t_user WHERE id={user_id}
 *      &#64;LazyFetch(column = "user_id")
 *  	private TUser user;
 *
 * }
 *
 * </code></pre>
 *
 * @author tanghc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface LazyFetch {

    /**
     * @return 对应的数据库字段名
     */
    String column();
}

package cn.printf.springbootboilerplate.common;

import javax.persistence.criteria.JoinType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {

    String propName() default "";

    Type type() default Type.EQUAL;

    String joinName() default "";

    JoinType join() default JoinType.LEFT;

    /**
     * 多字段模糊搜索，例如 name,phone
     */
    String blurry() default "";

    enum Type {
        EQUAL,
        GREATER_THAN,
        LESS_THAN,
        INNER_LIKE,
        LEFT_LIKE,
        RIGHT_LIKE,
        LESS_THAN_NQ,
        IN
    }
}


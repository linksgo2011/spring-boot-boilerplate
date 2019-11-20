package springbootboilerplate.annotation;

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

    Join join() default Join.LEFT;

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

    enum Join {
        LEFT, RIGHT
    }
}


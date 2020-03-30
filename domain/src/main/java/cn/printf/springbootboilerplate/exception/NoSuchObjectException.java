package cn.printf.springbootboilerplate.exception;

import static java.lang.String.format;

public class NoSuchObjectException extends RuntimeException {
    public NoSuchObjectException(Class<?> clazz, Object id) {
        super(format("Cannot find %s by Id: %s", clazz.getSimpleName(), id));
    }
    public NoSuchObjectException(String message) {
        super(message);
    }
}

package springbootboilerplate.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchObjectException extends RuntimeException {
    public NoSuchObjectException(Class<?> clazz, Object id) {
        super(format("Cannot find %s by Id: %s", clazz.getSimpleName(), id));
    }
    public NoSuchObjectException(String message) {
        super(message);
    }
}

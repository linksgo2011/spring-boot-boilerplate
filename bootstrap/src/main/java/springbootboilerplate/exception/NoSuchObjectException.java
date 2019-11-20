package springbootboilerplate.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

@ResponseStatus(PRECONDITION_FAILED)
public class NoSuchObjectException extends RuntimeException {
    public NoSuchObjectException(Class<?> clazz, Object id) {
        super(format("Cannot find %s by Id: %s", clazz.getSimpleName(), id));
    }

    public NoSuchObjectException(String message) {
        super(message);
    }
}

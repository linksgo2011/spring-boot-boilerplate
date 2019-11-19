package springbootboilerplate.exception;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

@ResponseStatus(PRECONDITION_FAILED)
public class ObjectExistException extends RuntimeException {

    public ObjectExistException(Class clazz, String field, String val) {
        super(ObjectExistException.generateMessage(clazz.getSimpleName(), field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity)
                + " with " + field + " " + val + " existed";
    }
}

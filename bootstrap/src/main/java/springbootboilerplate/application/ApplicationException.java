package springbootboilerplate.application;

import lombok.Getter;

/**
 * 发生在应用中的各种异常
 */
@Getter
public class ApplicationException extends RuntimeException {
    private String code;

    public ApplicationException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }
}

package cn.printf.springbootboilerplate.domain;

import lombok.Getter;

/**
 * 发生在领域内的各种异常
 */
@Getter
public class BusinessException extends RuntimeException {
    private String code;

    public BusinessException(String message, Throwable cause, String code) {
        super(message, cause);
        this.code = code;
    }
}

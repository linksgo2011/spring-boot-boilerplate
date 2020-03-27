package cn.printf.springbootboilerplate.domain.user;

import cn.printf.springbootboilerplate.domain.BusinessException;


public class UserExistException extends BusinessException {
    public UserExistException() {
        super("user_exist", "user exist", null);
    }
}

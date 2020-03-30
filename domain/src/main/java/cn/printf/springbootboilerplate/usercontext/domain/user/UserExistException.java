package cn.printf.springbootboilerplate.usercontext.domain.user;

import cn.printf.springbootboilerplate.exception.BusinessException;


public class UserExistException extends BusinessException {
    public UserExistException() {
        super("user_exist", "user exist", null);
    }
}

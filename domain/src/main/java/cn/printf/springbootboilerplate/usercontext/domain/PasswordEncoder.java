package cn.printf.springbootboilerplate.usercontext.domain;

public interface PasswordEncoder {
    String encode(String password);
}

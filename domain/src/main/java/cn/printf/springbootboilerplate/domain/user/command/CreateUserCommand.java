package cn.printf.springbootboilerplate.domain.user.command;

import cn.printf.springbootboilerplate.domain.BusinessException;
import lombok.Data;

@Data
public class CreateUserCommand {
    private String username;

    private String email;

    private String phone;

    private Boolean enabled;

    private String password;

    public static class CreateUserResult {
        private String username;

        private String email;

        private String phone;

        private Boolean enabled;

        private String password;
    }

    public static class UserAlreadyExistsException extends BusinessException {
        public UserAlreadyExistsException() {
            super("user already exists", null, "USER_ALREADY_EXISTS");
        }
    }
}

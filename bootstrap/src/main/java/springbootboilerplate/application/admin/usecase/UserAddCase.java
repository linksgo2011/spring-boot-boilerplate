package springbootboilerplate.application.admin.usecase;

import cn.printf.springbootboilerplate.usercontext.domain.command.UserAddCommand;
import cn.printf.springbootboilerplate.usercontext.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;

/**
 * 用例应该成对出现，外部类负责组装，充当 assembler 的功能
 */
public class UserAddCase {

    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @Data
    public static class Request {
        @NotNull
        private String username;
        @NotNull
        private String email;
        @NotNull
        private String phone;
        @NotNull
        private Boolean enabled;
        @NotNull
        private Long departmentId;
    }

    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @Data
    public static class Response {
        private String username;
        private String email;
        private String phone;
        private Boolean enabled;
        private Long departmentId;
    }

    public static Response toResponseFrom(User user) {
        return new ModelMapper().map(user, Response.class);
    }

    public static UserAddCommand toCommandFrom(Request request) {
        return new ModelMapper().map(request, UserAddCommand.class);
    }
}

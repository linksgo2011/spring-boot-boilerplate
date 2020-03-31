package springbootboilerplate.application.admin.usecase;

import cn.printf.springbootboilerplate.usercontext.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

public class GetUserCase {
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
}

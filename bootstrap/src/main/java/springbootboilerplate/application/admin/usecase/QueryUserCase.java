package springbootboilerplate.application.admin.usecase;

import cn.printf.springbootboilerplate.usercontext.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

public class QueryUserCase {
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @Data
    public static class Response {
        private Long id;

        private String username;

        private String email;

        private String phone;

        private Boolean enabled;
    }

    public static Response toResponseFrom(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, Response.class);
    }
}

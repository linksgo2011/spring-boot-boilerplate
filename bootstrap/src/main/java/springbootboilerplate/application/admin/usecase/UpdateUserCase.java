package springbootboilerplate.application.admin.usecase;

import cn.printf.springbootboilerplate.usercontext.domain.command.UserEditCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;


public class UpdateUserCase {

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
    }

    public static UserEditCommand toCommandFromRequest(UpdateUserCase.Request request) {
        return new ModelMapper().map(request, UserEditCommand.class);
    }
}

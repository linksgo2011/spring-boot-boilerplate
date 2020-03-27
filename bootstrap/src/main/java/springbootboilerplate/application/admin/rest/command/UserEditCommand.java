package springbootboilerplate.application.admin.rest.command;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class UserEditCommand {
    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private Boolean enabled;
}

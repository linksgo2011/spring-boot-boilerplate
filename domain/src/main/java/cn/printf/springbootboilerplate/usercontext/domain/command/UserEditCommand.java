package cn.printf.springbootboilerplate.usercontext.domain.command;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserEditCommand {
    private String username;

    private String email;

    private String phone;

    private Boolean enabled;
}

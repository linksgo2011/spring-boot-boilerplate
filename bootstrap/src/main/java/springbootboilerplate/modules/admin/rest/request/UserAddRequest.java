package springbootboilerplate.modules.admin.rest.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class UserAddRequest {
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

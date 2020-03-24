package springbootboilerplate.modules.auth.rest.request;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class TokenRequest {
    @Size(min = 2, max = 36)
    private String username;

    @Size(min = 6)
    private String password;
}

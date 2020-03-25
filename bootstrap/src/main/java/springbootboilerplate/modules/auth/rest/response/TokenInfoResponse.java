package springbootboilerplate.modules.auth.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TokenInfoResponse {
    private String username;
    private List<String> roles;

    public static TokenInfoResponse of(String username, List<String> roles) {
        return new TokenInfoResponse(username, roles);
    }
}

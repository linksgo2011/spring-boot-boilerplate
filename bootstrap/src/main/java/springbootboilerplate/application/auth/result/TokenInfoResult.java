package springbootboilerplate.application.auth.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TokenInfoResult {
    private String username;
    private List<String> roles;

    public static TokenInfoResult of(String username, List<String> roles) {
        return new TokenInfoResult(username, roles);
    }
}

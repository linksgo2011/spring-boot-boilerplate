package springbootboilerplate.modules.auth.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String value;

    public static TokenResponse of(String token) {
        return new TokenResponse(token);
    }
}

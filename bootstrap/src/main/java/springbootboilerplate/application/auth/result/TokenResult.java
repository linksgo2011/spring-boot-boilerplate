package springbootboilerplate.application.auth.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResult {
    private String value;

    public static TokenResult of(String token) {
        return new TokenResult(token);
    }
}

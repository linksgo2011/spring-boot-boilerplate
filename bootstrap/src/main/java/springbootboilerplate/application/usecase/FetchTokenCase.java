package springbootboilerplate.application.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

public class FetchTokenCase {

    @NoArgsConstructor
    @Data
    public static class Request {
        @Size(min = 2, max = 36)
        private String username;

        @Size(min = 6)
        private String password;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Response {
        private String value;
    }

    public static Response toResponseFrom(String token) {
        return new Response(token);
    }
}

package springbootboilerplate.utils;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@NoArgsConstructor(access = PRIVATE)
public final class HttpResponseRenderer {

    public static void reply(HttpServletResponse response, HttpStatus statusCode,
                             String responseBody)
        throws IOException {
        response.setStatus(statusCode.value());
        response.setHeader("Content-Type", APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().println(responseBody);
    }
}

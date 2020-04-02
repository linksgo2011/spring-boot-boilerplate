package springbootboilerplate.application.usecase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class QueryTokenInfoCase {

    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @Data
    public static class Response {
        private String username;
        private List<String> authorities;
    }

    public static Response toResponseFrom(Authentication authentication) {
        return Optional.ofNullable(authentication).map(
                element -> {
                    User userDetails = (User) element.getPrincipal();
                    List<String> authorities = element
                            .getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
                    return Response.of(userDetails.getUsername(), authorities);
                }).orElse(null);
    }
}

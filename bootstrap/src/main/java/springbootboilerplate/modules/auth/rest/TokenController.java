package springbootboilerplate.modules.auth.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springbootboilerplate.modules.auth.config.JWTTokenStore;
import springbootboilerplate.modules.auth.rest.request.TokenRequest;
import springbootboilerplate.modules.auth.rest.response.TokenInfoResponse;
import springbootboilerplate.modules.auth.rest.response.TokenResponse;
import springbootboilerplate.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/v1/token")
public class TokenController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTTokenStore tokenStore;

    @Autowired
    UserDetailsService userDetails;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse token(@Valid @RequestBody TokenRequest tokenRequest) {
        // 验证用户名，等信息
        return TokenResponse.of(tokenStore.generateToken(
                userDetails.loadUserByUsername(tokenRequest.getUsername())
        ));
    }

    /**
     * 查询当前 token 的有效信息，一般来说，如果使用了 JWT 客户端可以从 token 中解析出需要的信息
     *
     * @return
     */
    @GetMapping(value = "/info")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public TokenInfoResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return Optional.ofNullable(authentication).map(
                element -> {
                    String username = (String) element.getPrincipal();

                    List<String> roles = element
                            .getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
                    return TokenInfoResponse.of(username, roles);
                }).orElse(null);
    }
}

package springbootboilerplate.application.auth.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springbootboilerplate.application.auth.AuthApplicationService;
import springbootboilerplate.application.auth.command.FetchTokenCommand;
import springbootboilerplate.application.auth.result.TokenInfoResult;
import springbootboilerplate.application.auth.result.TokenResult;

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
    AuthApplicationService authApplicationService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResult token(@Valid @RequestBody FetchTokenCommand fetchTokenCommand) {
        return TokenResult.of(authApplicationService.auth(fetchTokenCommand));
    }

    /**
     * 查询当前 token 的有效信息，一般来说，如果使用了 JWT 客户端可以从 token 中解析出需要的信息
     *
     * @return
     */
    @GetMapping(value = "/info")
    public TokenInfoResult getUserInfo() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        return Optional.ofNullable(authentication).map(
                element -> {
                    User userDetails = (User) element.getPrincipal();

                    List<String> roles = element
                            .getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
                    return TokenInfoResult.of(userDetails.getUsername(), roles);
                }).orElse(null);
    }
}

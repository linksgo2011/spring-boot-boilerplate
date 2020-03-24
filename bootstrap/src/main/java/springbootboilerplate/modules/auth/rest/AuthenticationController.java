package springbootboilerplate.modules.auth.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springbootboilerplate.modules.auth.config.JWTTokenStore;
import springbootboilerplate.modules.auth.rest.request.TokenRequest;
import springbootboilerplate.modules.auth.rest.response.TokenResponse;
import springbootboilerplate.repository.UserRepository;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/token")
public class AuthenticationController {

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
     * 查询当前 token 的有效信息
     *
     * @return
     */
    @GetMapping(value = "/info")
    public UserDetails getUserInfo() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userDetails;
    }
}

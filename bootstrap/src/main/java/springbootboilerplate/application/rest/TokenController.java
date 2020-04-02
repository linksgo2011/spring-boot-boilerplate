package springbootboilerplate.application.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import springbootboilerplate.application.JWTTokenStore;
import springbootboilerplate.application.usecase.FetchTokenCase;
import springbootboilerplate.application.usecase.QueryTokenInfoCase;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/token")
public class TokenController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTTokenStore tokenStore;

    @Autowired
    UserDetailsService userDetails;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public FetchTokenCase.Response token(@Valid @RequestBody FetchTokenCase.Request fetchTokenCaseRequest) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                fetchTokenCaseRequest.getUsername(),
                fetchTokenCaseRequest.getPassword()
        );

        // 使用 provider 的 authenticationManager好处是支持多种数据来源，例如 DB、LDAP，并且会验证用户的 disable 等属性
        Authentication authentication = authenticationManager.authenticate(authRequest);
        return FetchTokenCase.toResponseFrom(
                tokenStore.generateToken((UserDetails) authentication.getPrincipal())
        );
    }

    /**
     * 查询当前 token 的有效信息，一般来说，如果使用了 JWT 客户端可以从 token 中解析出需要的信息
     *
     * @return
     */
    @GetMapping(value = "/info")
    public QueryTokenInfoCase.Response getUserInfo() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        return QueryTokenInfoCase.toResponseFrom(authentication);
    }
}

package springbootboilerplate.application.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import springbootboilerplate.application.auth.command.FetchTokenCommand;
import springbootboilerplate.application.auth.config.JWTTokenStore;

@Component
public class AuthCommandHandler {
    @Autowired
    JWTTokenStore tokenStore;

    @Autowired
    UserDetailsService userDetails;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String auth(FetchTokenCommand fetchTokenCommand) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                fetchTokenCommand.getUsername(),
                fetchTokenCommand.getPassword()
        );

        // 使用 provider 的 authenticationManager好处是支持多种数据来源，例如 DB、LDAP，并且会验证用户的 disable 等属性
        Authentication authentication = authenticationManager.authenticate(authRequest);

        return tokenStore.generateToken((UserDetails) authentication.getPrincipal());
    }
}

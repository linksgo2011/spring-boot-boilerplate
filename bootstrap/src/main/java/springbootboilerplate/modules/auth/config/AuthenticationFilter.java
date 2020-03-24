package springbootboilerplate.modules.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String TOKEN_HEADER = "Authorization";

    @Autowired
    private JWTTokenStore tokenStore;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String tokenString = httpRequest.getHeader(TOKEN_HEADER);
        // 可能存在多个 filter，如果已经验证成功跳过
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            // 构建 auth token，包括验证 token 的有效性
            AbstractAuthenticationToken authToken = this.tokenStore.buildAuthentication(tokenString);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        
        chain.doFilter(request, response);
    }
}

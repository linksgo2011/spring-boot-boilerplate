package springbootboilerplate.application.auth;

import com.google.common.base.Strings;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleTokenAuthenticationFilter extends GenericFilterBean
        implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher eventPublisher;
    private AuthenticationEntryPoint authenticationEntryPoint;


    private static final String TOKEN_HEADER = "Authorization";

    private JWTTokenStore tokenStore;

    public void setTokenStore(JWTTokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String tokenString = httpRequest.getHeader(TOKEN_HEADER);

        // 可能存在多个 filter，如果已经验证成功跳过
        if (SecurityContextHolder.getContext().getAuthentication() == null && !Strings.isNullOrEmpty(tokenString)) {
            try {
                // 构建 auth token，包括验证 token 的有效性
                AbstractAuthenticationToken authToken = this.tokenStore.buildAuthentication(tokenString);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                // fire event
                if (this.eventPublisher != null) {
                    eventPublisher.publishEvent(
                            new InteractiveAuthenticationSuccessEvent(authToken, this.getClass())
                    );
                }
            } catch (AuthenticationException authException) {
                authenticationEntryPoint.commence(httpRequest, httpResponse, authException);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }
}

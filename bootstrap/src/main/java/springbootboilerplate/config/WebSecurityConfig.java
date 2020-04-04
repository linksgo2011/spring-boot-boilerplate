package springbootboilerplate.config;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import springbootboilerplate.application.auth.JWTTokenStore;
import springbootboilerplate.application.auth.SimpleTokenAuthenticationFilter;
import springbootboilerplate.application.auth.CustomUserDetailService;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService identityService;

    @Autowired
    private JWTTokenStore jwtTokenStore;

    public WebSecurityConfig(CustomUserDetailService identityService) {
        this.identityService = identityService;
    }

    /**
     * 配置一个 authentication manager
     */
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.identityService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    SimpleTokenAuthenticationFilter authenticationTokenFilterBean() throws Exception {
        // 只有 header 中带有认证信息的才使用这个过滤器
        SimpleTokenAuthenticationFilter simpleTokenAuthenticationFilter = new SimpleTokenAuthenticationFilter();
        simpleTokenAuthenticationFilter.setAuthenticationEntryPoint(
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
        );
        simpleTokenAuthenticationFilter.setTokenStore(jwtTokenStore);
        return simpleTokenAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭 csrf（跨站脚本攻击） 功能，构建 Restful API 无法避免
                .csrf().disable()
                // 配置一个鉴权失败后返回的错误码
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                // 配置 session 管理器，关闭 session 管理，也不会创建 SecurityContext
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 开始配置权限设置
                .and().authorizeRequests()
                // 登录相关无需认证
                .antMatchers("/api/v1/token").permitAll()
                // 剩下的所有请求都需要认证
                .anyRequest().authenticated();

        // 设置 frame origin，防止 frame 的 xss 攻击
        http.headers().frameOptions().sameOrigin();
        // 允许自定义缓存控制
        http.headers().cacheControl();
        // 将自定义的过滤器置于 UsernamePassword 过滤器之前
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // 关闭无用的过滤器
        http.formLogin().disable();
        http.logout().disable();
    }

    /**
     * 配置密码的  hash 方式, BCrypt 牺牲加密效率，提高安全性
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final class TokenRequestMatcher implements RequestMatcher {
        private static final String TOKEN_HEADER = "Authorization";

        @Override
        public boolean matches(HttpServletRequest request) {
            String tokenString = request.getHeader(TOKEN_HEADER);
            return !Strings.isNullOrEmpty(tokenString);
        }
    }
}

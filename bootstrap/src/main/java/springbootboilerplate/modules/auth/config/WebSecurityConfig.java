package springbootboilerplate.modules.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springbootboilerplate.modules.auth.CustomUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService identityService;

    private final EntryPointUnauthorizedHandler unauthorizedHandler;

    public WebSecurityConfig(EntryPointUnauthorizedHandler unauthorizedHandler,
                             CustomUserDetailService identityService) {
        this.unauthorizedHandler = unauthorizedHandler;
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
    public AuthenticationFilter authenticationTokenFilterBean() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 关闭 csrf（跨站脚本攻击） 功能，构建 Restful API 无法避免
                .csrf().disable()
                // 配置一个鉴权失败后返回的错误码
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                // 配置 session 管理器，关闭 session 管理，也不会创建 SecurityContext
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 开始配置权限设置
                .and().authorizeRequests()
                // 登录相关无需认证
                .antMatchers("/v1/api/token/*").permitAll()
                // 剩下的所有请求都需要认证
                .anyRequest().authenticated();

        // 设置 frame origin，防止 frame 的 xss 攻击
        http.headers().frameOptions().sameOrigin();
        // 允许自定义缓存控制
        http.headers().cacheControl();
        // 将自定义的过滤器置于 UsernamePassword 过滤器之前
        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        http.formLogin().disable();
    }

    /**
     * 配置密码的  hash 方式, BCrypt 牺牲加密效率，提高安全性
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

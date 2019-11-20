package springbootboilerplate.modules.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springbootboilerplate.modules.auth.AwareAuthenticationSuccessHandler;
import springbootboilerplate.modules.auth.CustomUserDetailService;
import springbootboilerplate.modules.auth.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/api/frontend/**").authenticated()
                .antMatchers("/api/admin/**").hasRole("admin")
                .and()
                .formLogin()
                // no need to redirect when login success
                .successHandler(new AwareAuthenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .and()
                .csrf()
                .disable();
    }


    @Autowired
    CustomUserDetailService customUserDetailService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

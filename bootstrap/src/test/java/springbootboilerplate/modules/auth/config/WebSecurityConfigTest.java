package springbootboilerplate.modules.auth.config;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class WebSecurityConfigTest {

    @Test
    public void passwordEncoder() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}

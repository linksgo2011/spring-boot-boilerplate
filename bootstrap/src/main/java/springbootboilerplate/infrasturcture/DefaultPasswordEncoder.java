package springbootboilerplate.infrasturcture;

import cn.printf.springbootboilerplate.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultPasswordEncoder implements PasswordEncoder {
    private BCryptPasswordEncoder encoder;

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }
}

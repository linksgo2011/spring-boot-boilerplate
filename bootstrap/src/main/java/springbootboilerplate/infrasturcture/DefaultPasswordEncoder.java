package springbootboilerplate.infrasturcture;

import cn.printf.springbootboilerplate.usercontext.domain.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultPasswordEncoder implements PasswordEncoder {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public String encode(String password) {
        return encoder.encode(password);
    }
}

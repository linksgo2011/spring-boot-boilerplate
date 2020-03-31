package springbootboilerplate.application.auth.fixture;

import cn.printf.springbootboilerplate.usercontext.domain.PasswordEncoder;
import cn.printf.springbootboilerplate.usercontext.domain.user.User;
import cn.printf.springbootboilerplate.usercontext.domain.user.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Builder
@Service
public class UserFixture {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User createAdminUser() {
        String password = "123456";
        String hashedPassword = passwordEncoder.encode(password);
        return userRepository.save(prepareAdminUser(hashedPassword));
    }

    public static User prepareAdminUser(String encodePassword) {
        User user = new User();
        user.setUsername("zhangsan");
        user.setEmail("zhangsan@email.com");
        user.setPhone("13668193903");
        user.setEnabled(true);
        user.setPassword(encodePassword);
        user.setCreateAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        return user;
    }
}

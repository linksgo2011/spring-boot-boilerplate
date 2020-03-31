package springbootboilerplate.application.auth.fixture;

import cn.printf.springbootboilerplate.usercontext.domain.PasswordEncoder;
import cn.printf.springbootboilerplate.usercontext.domain.user.User;
import cn.printf.springbootboilerplate.usercontext.domain.user.UserRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;

@Builder
@Service
public class UserFixture {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserRepository userRepository;

    public User createAdminUser() {
        String password = "123456";
        String hashedPassword = passwordEncoder.encode(password);
        return userRepository.save(prepareAdminUser(hashedPassword));
    }

    public User createNormalUser() {
        String password = "123456";
        String hashedPassword = passwordEncoder.encode(password);
        return userRepository.save(prepareNormalUser(hashedPassword));
    }

    public static User prepareAdminUser(String encodePassword) {
        User user = new User();
        user.setUsername("zhangsan");
        user.setEmail("zhangsan@email.com");
        user.setPhone("13668193903");
        user.setEnabled(true);
        user.setPassword(encodePassword);
        user.setRoles(new ArrayList() {{
            add("ADMIN");
        }});
        user.setCreateAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        return user;
    }

    public static User prepareNormalUser(String encodePassword) {
        User user = new User();
        user.setUsername("lisi");
        user.setEmail("lisi@email.com");
        user.setPhone("13668193903");
        user.setEnabled(true);
        user.setPassword(encodePassword);
        user.setRoles(new ArrayList() {{
            add("USER");
        }});
        user.setCreateAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        return user;
    }
}

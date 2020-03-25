package springbootboilerplate.modules.auth;

import cn.printf.springbootboilerplate.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import springbootboilerplate.repository.UserRepository;

/**
 * UserDetails 存权限系统的用户来源，只需要在获取凭证时读取用户信息，然后存储到 redis、jwt 中
 */
@Primary
@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not find by username"));
        return CustomUserDetails.from(user);
    }
}

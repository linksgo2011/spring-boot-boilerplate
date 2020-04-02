package cn.printf.springbootboilerplate.usercontext.domain;

import cn.printf.springbootboilerplate.common.CriteriaHelper;
import cn.printf.springbootboilerplate.usercontext.domain.command.UserAddCommand;
import cn.printf.springbootboilerplate.usercontext.domain.command.UserEditCommand;
import cn.printf.springbootboilerplate.usercontext.domain.user.User;
import cn.printf.springbootboilerplate.usercontext.domain.user.UserCriteria;
import cn.printf.springbootboilerplate.usercontext.domain.user.UserExistException;
import cn.printf.springbootboilerplate.usercontext.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class UserDomainService {
    private static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional(rollbackFor = Exception.class)
    public User addUser(UserAddCommand userAddCommand) {
        if (userRepository.findByUsername(userAddCommand.getUsername()).isPresent()) {
            throw new UserExistException();
        }

        if (userRepository.findByEmail(userAddCommand.getEmail()).isPresent()) {
            throw new UserExistException();
        }

        String hashedPassword = encoder.encode(DEFAULT_PASSWORD);
        User user = User
                .builder()
                .email(userAddCommand.getEmail())
                .username(userAddCommand.getUsername())
                .phone(userAddCommand.getPhone())
                .enabled(userAddCommand.getEnabled())
                .departmentId(userAddCommand.getDepartmentId())
                .password(hashedPassword)
                .build();

        return userRepository.saveAndFlush(user);
    }

    /**
     * 删除接口设计成幂等，客户端报错后可以重试
     */
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresent(user -> userRepository.delete(user));
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUser(Long userId, UserEditCommand userEditCommand) {
        User user = userRepository.getOne(userId);

        user.setEnabled(userEditCommand.getEnabled());
        user.setEmail(userEditCommand.getEmail());
        user.setPhone(userEditCommand.getPhone());
        user.setUsername(userEditCommand.getUsername());
        user.setUpdateAt(new Timestamp(System.currentTimeMillis()));

        userRepository.saveAndFlush(user);
    }

    public Page<User> getUsers(UserCriteria userCriteria, Pageable pageable) {
        return userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> CriteriaHelper.getPredicate(root, userCriteria, criteriaBuilder), pageable);
    }

    public User getUser(Long userId) {
        return userRepository.getOne(userId);
    }
}

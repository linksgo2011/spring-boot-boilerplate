package springbootboilerplate.application.admin;

import cn.printf.springbootboilerplate.domain.department.Department;
import cn.printf.springbootboilerplate.domain.user.User;
import cn.printf.springbootboilerplate.domain.user.UserExistException;
import cn.printf.springbootboilerplate.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootboilerplate.application.admin.rest.command.UserAddCommand;
import springbootboilerplate.application.admin.rest.command.UserEditCommand;
import springbootboilerplate.application.admin.rest.query.UserCriteria;
import springbootboilerplate.application.admin.rest.result.PageResource;
import springbootboilerplate.application.admin.rest.result.UserResource;
import springbootboilerplate.utils.CriteriaHelper;

import static springbootboilerplate.config.Constants.DEFAULT_PASSWORD;

@Service
@AllArgsConstructor
public class UserAdminAppService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public PageResource getUsers(UserCriteria userCriteria, Pageable pageable) {
        Page<UserResource> page = userRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) -> CriteriaHelper.getPredicate(root, userCriteria, criteriaBuilder), pageable
        ).map(UserResource::of);
        return PageResource.toResource(page);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserResource addUser(UserAddCommand userAddCommand) {
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
                .username(userAddCommand.getEmail())
                .phone(userAddCommand.getPhone())
                .enabled(userAddCommand.getEnabled())
                .password(hashedPassword)
                .department(Department.builder().id(userAddCommand.getDepartmentId()).build())
                .build();

        User savedUser = userRepository.saveAndFlush(user);
        return UserResource.of(savedUser);
    }

    @Transactional(rollbackFor = Exception.class)
    public UserResource updateUser(Long userId, UserEditCommand userAddRequest) {
        User user = userRepository.getOne(userId);

        user.setEnabled(userAddRequest.getEnabled());
        user.setEmail(userAddRequest.getEmail());
        user.setPhone(userAddRequest.getPhone());
        user.setUsername(userAddRequest.getUsername());

        User savedUser = userRepository.saveAndFlush(user);
        return UserResource.of(savedUser);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}

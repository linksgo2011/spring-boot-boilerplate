package springbootboilerplate.modules.admin.application;

import cn.printf.springbootboilerplate.domain.Department;
import cn.printf.springbootboilerplate.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootboilerplate.exception.NoSuchObjectException;
import springbootboilerplate.exception.ObjectExistException;
import springbootboilerplate.repository.UserRepository;
import springbootboilerplate.modules.admin.rest.request.UserAddRequest;
import springbootboilerplate.modules.admin.rest.request.UserCriteria;
import springbootboilerplate.modules.admin.rest.request.UserEditRequest;
import springbootboilerplate.modules.admin.rest.resource.PageResource;
import springbootboilerplate.modules.admin.rest.resource.UserResource;
import springbootboilerplate.utils.CriteriaHelper;

import static springbootboilerplate.config.Constants.DEFAULT_PASSWORD;

@Service
@AllArgsConstructor
public class UserService {

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
    public UserResource addUser(UserAddRequest userAddRequest) {
        if (userRepository.findByUsername(userAddRequest.getUsername()).isPresent()) {
            throw new ObjectExistException(User.class, "username", userAddRequest.getUsername());
        }

        if (userRepository.findByEmail(userAddRequest.getEmail()).isPresent()) {
            throw new ObjectExistException(User.class, "email", userAddRequest.getUsername());
        }

        String hashedPassword = encoder.encode(DEFAULT_PASSWORD);
        User user = User
                .builder()
                .email(userAddRequest.getEmail())
                .username(userAddRequest.getEmail())
                .phone(userAddRequest.getPhone())
                .enabled(userAddRequest.getEnabled())
                .password(hashedPassword)
                .department(Department.builder().id(userAddRequest.getDepartmentId()).build())
                .build();

        User savedUser = userRepository.saveAndFlush(user);
        return UserResource.of(savedUser);
    }

    public UserResource updateUser(Long userId, UserEditRequest userAddRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchObjectException("user not found"));

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

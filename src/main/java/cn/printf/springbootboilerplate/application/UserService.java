package cn.printf.springbootboilerplate.application;

import cn.printf.springbootboilerplate.domain.User;
import cn.printf.springbootboilerplate.exception.NoSuchObjectException;
import cn.printf.springbootboilerplate.repository.UserRepository;
import cn.printf.springbootboilerplate.rest.request.UserAddRequest;
import cn.printf.springbootboilerplate.rest.request.UserCriteria;
import cn.printf.springbootboilerplate.rest.request.UserEditRequest;
import cn.printf.springbootboilerplate.rest.resource.PageResource;
import cn.printf.springbootboilerplate.rest.resource.UserResource;
import cn.printf.springbootboilerplate.utils.CriteriaHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public PageResource getUsers(UserCriteria userCriteria, Pageable pageable) {
        Page<UserResource> page = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> CriteriaHelper.getPredicate(root, userCriteria, criteriaBuilder), pageable).map(UserResource::of);
        return PageResource.toResource(page);
    }

    public UserResource addUser(UserAddRequest userAddRequest) {
        User user = User
                .builder()
                .email(userAddRequest.getEmail())
                .username(userAddRequest.getEmail())
                .phone(userAddRequest.getPhone())
                .enabled(userAddRequest.getEnabled())
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

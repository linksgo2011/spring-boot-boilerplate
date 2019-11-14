package cn.printf.springbootboilerplate.rest;

import cn.printf.springbootboilerplate.rest.responses.UserResponse;
import cn.printf.springbootboilerplate.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class UserController {

    private UserService userService;

    @RequestMapping("/users")
    public Collection<UserResponse> getUsers() {
        return userService
                .getUserList()
                .stream()
                .map(user -> UserResponse.builder().username(user.getUsername())
                        .build()).collect(Collectors.toList());
    }
}

package cn.printf.springbootboilerplate.rest;

import cn.printf.springbootboilerplate.application.UserService;
import cn.printf.springbootboilerplate.rest.request.UserAddRequest;
import cn.printf.springbootboilerplate.rest.request.UserCriteria;
import cn.printf.springbootboilerplate.rest.request.UserEditRequest;
import cn.printf.springbootboilerplate.rest.resource.PageResource;
import cn.printf.springbootboilerplate.rest.resource.UserResource;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @GetMapping
    public PageResource getUsers(UserCriteria userCriteria, Pageable pageable) {
        return userService.getUsers(userCriteria, pageable);
    }

    @PostMapping
    public ResponseEntity<UserResource> addUser(@RequestBody @Valid UserAddRequest userAddRequest) {
        UserResource userResource = userService.addUser(userAddRequest);

        return ResponseEntity.created(
                URI.create("/api/users/" + userResource.getId())
        ).body(userResource);
    }

    @PutMapping("{userId}")
    public UserResource updateUser(
            @PathVariable long userId,
            @RequestBody @Valid UserEditRequest userAddRequest
    ) {
        return userService.updateUser(userId, userAddRequest);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }
}

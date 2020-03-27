package springbootboilerplate.application.user.rest;

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
import springbootboilerplate.application.user.UserService;
import springbootboilerplate.application.user.rest.request.UserAddRequest;
import springbootboilerplate.application.user.rest.request.UserCriteria;
import springbootboilerplate.application.user.rest.request.UserEditRequest;
import springbootboilerplate.application.user.rest.resource.PageResource;
import springbootboilerplate.application.user.rest.resource.UserResource;

import javax.validation.Valid;
import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/users")
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

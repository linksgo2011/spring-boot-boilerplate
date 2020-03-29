package springbootboilerplate.application.admin.rest;

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
import springbootboilerplate.application.admin.UserAdminAppService;
import springbootboilerplate.application.admin.command.UserAddCommand;
import springbootboilerplate.application.admin.query.UserCriteria;
import springbootboilerplate.application.admin.command.UserEditCommand;
import springbootboilerplate.application.admin.result.PageResource;
import springbootboilerplate.application.admin.result.UserResource;

import javax.validation.Valid;
import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private UserAdminAppService userAdminAppService;

    /**
     * 根据搜索条件,获取用户列表
     */
    @GetMapping
    public PageResource<UserResource> getUsers(UserCriteria userCriteria, Pageable pageable) {
        return userAdminAppService.getUsers(userCriteria, pageable);
    }

    @PostMapping
    public ResponseEntity<UserResource> addUser(@RequestBody @Valid UserAddCommand userAddCommand) {
        UserResource userResource = userAdminAppService.addUser(userAddCommand);

        return ResponseEntity.created(
                URI.create("/api/users/" + userResource.getId())
        ).body(userResource);
    }

    @PutMapping("{userId}")
    public UserResource updateUser(
            @PathVariable long userId,
            @RequestBody @Valid UserEditCommand userAddRequest
    ) {
        return userAdminAppService.updateUser(userId, userAddRequest);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable long userId) {
        userAdminAppService.deleteUser(userId);
    }
}

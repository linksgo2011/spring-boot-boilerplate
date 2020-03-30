package springbootboilerplate.application.admin.rest;

import cn.printf.springbootboilerplate.usercontext.domain.user.User;
import cn.printf.springbootboilerplate.usercontext.domain.user.UserCriteria;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import cn.printf.springbootboilerplate.usercontext.domain.UserDomainService;
import springbootboilerplate.application.admin.usecase.UserAddCase;
import springbootboilerplate.application.admin.usecase.UserEditCase;
import springbootboilerplate.application.admin.usecase.UserQueryCase;

import javax.validation.Valid;
import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    @Autowired
    private UserDomainService userDomainService;

    /**
     * 根据搜索条件,获取用户列表
     */
    @GetMapping
    public Page<UserQueryCase.Response> getUsers(UserCriteria userCriteria, Pageable pageable) {
        return userDomainService.getUsers(userCriteria, pageable).map(UserQueryCase::toResponseFrom);
    }

    @PostMapping
    public ResponseEntity<UserAddCase.Response> addUser(@RequestBody @Valid UserAddCase.Request userAddRequest) {
        // 对数据进行拆解，让不同的 domain service 执行
        User user = userDomainService.addUser(UserAddCase.toCommandFrom(userAddRequest));
        return ResponseEntity.created(
                URI.create("/api/users/" + user.getId())
        ).body(UserAddCase.toResponseFrom(user));
    }

    @PutMapping("{userId}")
    public void updateUser(
            @PathVariable long userId,
            @RequestBody @Valid UserEditCase.Request userEditRequest
    ) {
        userDomainService.updateUser(userId, UserEditCase.toCommandFromRequest(userEditRequest));
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable long userId) {
        userDomainService.deleteUser(userId);
    }
}

package springbootboilerplate.application.admin.rest;

import cn.printf.springbootboilerplate.usercontext.domain.UserDomainService;
import cn.printf.springbootboilerplate.usercontext.domain.user.User;
import cn.printf.springbootboilerplate.usercontext.domain.user.UserCriteria;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springbootboilerplate.application.admin.usecase.AddUserCase;
import springbootboilerplate.application.admin.usecase.GetUserCase;
import springbootboilerplate.application.admin.usecase.QueryUserCase;
import springbootboilerplate.application.admin.usecase.UpdateUserCase;

import javax.validation.Valid;
import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    @Autowired
    private UserDomainService userDomainService;

    @PostMapping
    public ResponseEntity<AddUserCase.Response> addUser(@RequestBody @Valid AddUserCase.Request userAddRequest) {
        // 对数据进行拆解，让不同的 domain service 执行
        User user = userDomainService.addUser(AddUserCase.toCommandFrom(userAddRequest));
        return ResponseEntity.created(
                URI.create("/api/users/" + user.getId())
        ).body(AddUserCase.toResponseFrom(user));
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable long userId) {
        userDomainService.deleteUser(userId);
    }

    @PutMapping("{userId}")
    public void updateUser(
            @PathVariable long userId,
            @RequestBody @Valid UpdateUserCase.Request userEditRequest
    ) {
        userDomainService.updateUser(userId, UpdateUserCase.toCommandFromRequest(userEditRequest));
    }

    /**
     * 根据搜索条件,获取用户列表
     */
    @GetMapping
    public Page<QueryUserCase.Response> queryUser(UserCriteria userCriteria, Pageable pageable) {
        return userDomainService.getUsers(userCriteria, pageable).map(QueryUserCase::toResponseFrom);
    }

    @GetMapping("{userId}")
    public GetUserCase.Response getUser(@PathVariable long userId) {
        return GetUserCase.toResponseFrom(userDomainService.getUser(userId));
    }
}

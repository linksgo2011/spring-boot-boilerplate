package springbootboilerplate.application.auth.fixture;

import cn.printf.springbootboilerplate.usercontext.domain.user.User;
import lombok.Builder;

import java.sql.Timestamp;

@Builder
public class UserFixture{

    public static User prepareAdminUser() {
        User user = new User();
        user.setUsername("zhangsan");
        user.setEmail("zhangsan@email.com");
        user.setPhone("13668193903");
        user.setEnabled(true);
        user.setPassword("123456");
        user.setCreateAt(new Timestamp(System.currentTimeMillis()));
        user.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        return user;
    }

}

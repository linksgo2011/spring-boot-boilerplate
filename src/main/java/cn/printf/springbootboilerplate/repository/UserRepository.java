package cn.printf.springbootboilerplate.repository;

import cn.printf.springbootboilerplate.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {
}

package cn.printf.springbootboilerplate.persistence;

import cn.printf.springbootboilerplate.model.QUser;
import cn.printf.springbootboilerplate.model.User;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@Transactional(readOnly = true)
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    private JPAQuery<User> from() {
        return new JPAQuery<User>(entityManager).from(QUser.user);
    }

    @Transactional
    public void save(User model) {
        entityManager.persist(model);
    }

    public List<User> findAll() {
        return from().fetch();
    }
}

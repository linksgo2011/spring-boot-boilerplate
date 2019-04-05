package cn.printf.springbootboilerplate.service;

import cn.printf.springbootboilerplate.model.User;
import cn.printf.springbootboilerplate.persistence.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserDao userDao;

    public List<User> getUserList() {
        return userDao.findAll();
    }
}

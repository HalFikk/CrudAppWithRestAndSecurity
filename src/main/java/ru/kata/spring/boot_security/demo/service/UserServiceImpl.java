package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDao;
    @Autowired
    public UserServiceImpl(UserDAO userDao) {
        this.userDao = userDao;
    }


    @Transactional
    public List<User> getAllUser() {
        return userDao.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Transactional
    public void deleteUser(long id) {
        userDao.deleteById(id);
    }

    @Transactional
    public void updateUser(long id, User user) {
        user.setId(id);
        userDao.save(user);
    }

    @Transactional
    public User getUser(long id) {
        return userDao.getById(id);
    }

    @Transactional
    public User getUserByUsername(String username) {
        Optional<User> user = userDao.findUserByEmailAndRoles(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Такого пользователя нет");
        }
        System.out.println(user.get().getRoles());
        return user.get();
    }
}

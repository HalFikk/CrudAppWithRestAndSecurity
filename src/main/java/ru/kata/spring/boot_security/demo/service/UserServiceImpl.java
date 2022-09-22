package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserDAO userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUser() {
        return userDao.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        if (user.getRole() != null) {
            user.setRoles(setRoleToUser(user));
        }
        userDao.save(user);
    }

    @Transactional
    public void deleteUser(long id) {
        userDao.deleteById(id);
    }

    @Transactional
    public void updateUser(long id, User user) {
        var userDB = userDao.getById(id);
        user.setId(id);
        if (user.getRole() != null) {
            user.setRoles(setRoleToUser(user));
        }
        if (!userDB.getPassword().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.save(user);
    }

    public User getUser(long id) {
        return userDao.getById(id);
    }

    public User getUserByUsername(String username) {
        Optional<User> user = userDao.getUserByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Такого пользователя нет");
        }
        return user.get();
    }

    public List<Role> setRoleToUser(User user) {
        List<Role> listRole = new ArrayList<>();
        if (user.getRole().toLowerCase().contains("admin")) {
            listRole.add(new Role(1, "ROLE_ADMIN"));
        }
        if (user.getRole().toLowerCase().contains("user")) {
            listRole.add(new Role(2, "ROLE_USER"));
        }
        return listRole;
    }
}

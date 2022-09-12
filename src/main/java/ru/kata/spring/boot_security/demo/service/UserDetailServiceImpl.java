package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    /*insert into user (name, surname, password, email, age) VALUES
('Alex', 'Loktaev', '$2a$12$DzZpsCnQMJitf4FdLyZsaeQEmdv65yD7kunqjLMP0/u98L9DAXsty', 'mail1@mail.ru', 26),
        ('Oleg', 'Krochak', '$2a$12$8flBSDWHKAsPXc.p3u5equxx3xifVhLtHjCa4qr25sE1/YVcUFTiG', 'mail2@mail.ru', 30),
        ('Emma', 'Ivanova', '$2a$12$/qJAAmXiGH2zCDt6A3esV.hZ/poXuUAIb92sW0ItSpIn7YOU/zqES', 'mail3@mail.ru', 40);*/

    private final UserDAO userDAO;

    @Autowired
    public UserDetailServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userDAO.findByEmail(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Такого пользователя не существует");
        }
        return new UserDetailsImpl(user.get());
    }
}

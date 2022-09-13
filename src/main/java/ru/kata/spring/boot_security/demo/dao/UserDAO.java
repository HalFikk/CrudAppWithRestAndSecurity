package ru.kata.spring.boot_security.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
    @Query
    Optional<User> findByEmail(String username);
    @Query ("from User user left join fetch user.roles WHERE user.email =:email")
    Optional<User> findUserByEmailAndRoles(@Param("email") String email);
}


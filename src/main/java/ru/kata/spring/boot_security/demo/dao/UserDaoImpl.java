package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUser() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(long id) {
        User deleteUser = entityManager.find(User.class, id);
        entityManager.remove(deleteUser);
    }

    @Override
    public void updateUser(long id, User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUser(long id) {
        return (User)entityManager.createQuery("select u from User u where u.id= :id")
                .setParameter("id", id).getSingleResult();
    }
}


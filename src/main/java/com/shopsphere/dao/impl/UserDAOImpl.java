package com.shopsphere.dao.impl;

import com.shopsphere.dao.UserDAO;
import com.shopsphere.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User update(User user) {
        return entityManager.merge(user);
    }

    @Override
    public User findById(Integer userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public User findByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u ORDER BY u.userId DESC", User.class)
                .getResultList();
    }

    @Override
    public void delete(Integer userId) {
        User user = findById(userId);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}

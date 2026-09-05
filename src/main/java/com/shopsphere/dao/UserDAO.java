package com.shopsphere.dao;

import com.shopsphere.entity.User;
import java.util.List;

public interface UserDAO {
    User save(User user);
    User update(User user);
    User findById(Integer userId);
    User findByEmail(String email);
    List<User> findAll();
    void delete(Integer userId);
}

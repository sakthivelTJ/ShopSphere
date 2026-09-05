package com.shopsphere.service.impl;

import com.shopsphere.dao.UserDAO;
import com.shopsphere.entity.User;
import com.shopsphere.exception.ResourceNotFoundException;
import com.shopsphere.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Integer userId) {
        User user = userDAO.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    @Transactional
    public User updateUserProfile(Integer userId, String fullName, String phone, String gender, String address) {
        User user = getUserById(userId);
        if (fullName != null && !fullName.trim().isEmpty()) user.setFullName(fullName);
        if (phone != null) user.setPhone(phone);
        if (gender != null) user.setGender(gender);
        if (address != null) user.setAddress(address);
        return userDAO.update(user);
    }
}

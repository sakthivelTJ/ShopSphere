package com.shopsphere.service;

import com.shopsphere.entity.User;
import java.util.List;

public interface UserService {
    User getUserById(Integer userId);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    User updateUserProfile(Integer userId, String fullName, String phone, String gender, String address);
}

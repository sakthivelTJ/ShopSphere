package com.shopsphere.service.impl;

import com.shopsphere.dao.UserDAO;
import com.shopsphere.dto.LoginRequest;
import com.shopsphere.dto.RegisterRequest;
import com.shopsphere.entity.User;
import com.shopsphere.exception.InvalidCredentialsException;
import com.shopsphere.service.AuthService;
import com.shopsphere.util.PasswordUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDAO userDAO;

    public AuthServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional
    public User registerUser(RegisterRequest registerRequest) {
        User existingUser = userDAO.findByEmail(registerRequest.getEmail());
        if (existingUser != null) {
            throw new InvalidCredentialsException("Email address is already registered!");
        }

        String hashedPassword = PasswordUtil.hashPassword(registerRequest.getPassword());
        User newUser = new User(
                registerRequest.getFullName(),
                registerRequest.getEmail(),
                registerRequest.getPhone(),
                hashedPassword,
                registerRequest.getGender(),
                registerRequest.getAddress(),
                "CUSTOMER"
        );
        return userDAO.save(newUser);
    }

    @Override
    @Transactional(readOnly = true)
    public User loginUser(LoginRequest loginRequest) {
        User user = userDAO.findByEmail(loginRequest.getEmail());
        if (user == null) {
            throw new InvalidCredentialsException("Invalid email or password!");
        }

        if (!PasswordUtil.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password!");
        }

        return user;
    }
}

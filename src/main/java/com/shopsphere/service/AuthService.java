package com.shopsphere.service;

import com.shopsphere.dto.LoginRequest;
import com.shopsphere.dto.RegisterRequest;
import com.shopsphere.entity.User;

public interface AuthService {
    User registerUser(RegisterRequest registerRequest);
    User loginUser(LoginRequest loginRequest);
}

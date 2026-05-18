package com.tcs.ilp.servease.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.entity.User;
import com.tcs.ilp.servease.entity.UserLogin;
import com.tcs.ilp.servease.repository.UserRepository;
import com.tcs.ilp.servease.repository.UserLoginRepository;
import com.tcs.ilp.servease.util.PasswordUtil;
import com.tcs.ilp.servease.dto.*;

@Service
public class LoginBO {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    public LoginResponse login(String userId, String password) {   // ✅ FIXED

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserLogin login = userLoginRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Login not found"));

        String hashedInput = PasswordUtil.hashPassword(password, login.getSalt());

        if (!hashedInput.equals(login.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        Role role;
        try {
            role = Role.valueOf(user.getRole().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid role in DB: " + user.getRole());
        }

        String sessionId = sessionManager.createSession(userId, role);

        return new LoginResponse(sessionId, role);
    }
}
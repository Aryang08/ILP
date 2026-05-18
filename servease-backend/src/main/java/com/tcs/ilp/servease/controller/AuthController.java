package com.tcs.ilp.servease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.LoginBO;
import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.dto.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LoginBO loginBO;

    @Autowired
    private SessionManager sessionManager;

    //  LOGIN (FIXED RETURN TYPE)
    @PostMapping("/login")
    public LoginResponse login(@RequestParam String userId,
                        @RequestParam String password) {

        return loginBO.login(userId, password);
    }

    //  LOGOUT (UNCHANGED)
    @PostMapping("/logout")
    public String logout(@RequestHeader("sessionId") String sessionId) {

        sessionManager.invalidateSession(sessionId);
        return "Logged out successfully";
    }
}
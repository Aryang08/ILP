package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.UserBO;
import com.tcs.ilp.servease.entity.User;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.config.Role;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserBO service;

    // ✅ NEW: inject SessionManager (mockable in tests)
    @Autowired
    private SessionManager sessionManager;

    // ✅ HELPER METHOD (centralized session handling)
    private SessionData getSession(HttpServletRequest request) {
        String sessionId = request.getHeader("sessionId");

        if (sessionId == null) {
            throw new RuntimeException("Missing sessionId");
        }

        SessionData session = sessionManager.getSession(sessionId);

        if (session == null) {
            throw new RuntimeException("Invalid session");
        }

        return session;
    }

    // ✅ CREATE USER (PUBLIC)
    @PostMapping
    public String createUser(@RequestBody User u) {
        return service.createUser(u);
    }

    // ✅ GET ALL USERS (ADMIN ONLY)
    @GetMapping
    public List<User> getAllUsers(HttpServletRequest request) {

        SessionData session = getSession(request);

        // ✅ Admin check
        if (session.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access Denied");
        }

        return service.getAllUsers();
    }

    // ✅ GET USER BY ID (ANY LOGGED-IN USER)
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId,
                           HttpServletRequest request) {

        getSession(request);   // only validation needed
        return service.getUserById(userId);
    }

    // ✅ UPDATE USER (ADMIN ONLY)
    @PutMapping
    public String updateUser(@RequestBody User u,
                            HttpServletRequest request) {

        SessionData session = getSession(request);

        if (session.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access Denied");
        }

        service.updateUser(u);
        return "Updated";
    }

    // ✅ DELETE USER (ADMIN ONLY)
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId,
                            HttpServletRequest request) {

        SessionData session = getSession(request);

        if (session.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access Denied");
        }

        service.deleteUser(userId);
        return "Deleted";
    }

    // ✅ RESET PASSWORD (PUBLIC)
    @PostMapping("/reset")
    public String resetPassword(@RequestParam String userId,
                               @RequestParam String newPassword) {
        return service.resetPassword(userId, newPassword);
    }
}
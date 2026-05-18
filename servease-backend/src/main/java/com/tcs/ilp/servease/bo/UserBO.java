package com.tcs.ilp.servease.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.entity.User;
import com.tcs.ilp.servease.entity.UserLogin;
import com.tcs.ilp.servease.repository.UserRepository;
import com.tcs.ilp.servease.repository.UserLoginRepository;
import com.tcs.ilp.servease.util.PasswordUtil;

@Service
public class UserBO {

    @Autowired
    private UserRepository userDAO;

    @Autowired
    private UserLoginRepository loginDAO;

    @Autowired
    private SessionManager sessionManager;

    // =========================
    // CREATE USER
    // =========================
    public String createUser(User u) {

        if (userDAO.existsById(u.getUserId())) {
            return "User already exists";
        }

        userDAO.save(u);

        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hashPassword("Cust@123", salt);

        UserLogin login = new UserLogin(u.getUserId(), hash, salt, true);
        loginDAO.save(login);

        return "User Created";
    }

    // =========================
    // GET ALL
    // =========================
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    // =========================
    // GET BY ID
    // =========================
    public User getUserById(String userId) {
        return userDAO.findById(userId).orElse(null);
    }

    // =========================
    // UPDATE
    // =========================
    public void updateUser(User u) {
        userDAO.save(u);
    }

    // =========================
    // DELETE
    // =========================
    public void deleteUser(String userId) {
        loginDAO.deleteById(userId);
        userDAO.deleteById(userId);
    }

    // =========================
    // LOGIN ✅ FIXED
    // =========================
    public String login(String userId, String password) {

        UserLogin login = loginDAO.findById(userId).orElse(null);
        if (login == null) return "INVALID";

        String hash = PasswordUtil.hashPassword(password, login.getSalt());
        if (!hash.equals(login.getPasswordHash())) return "INVALID";

        if (login.isFirstLogin()) return "RESET_PASSWORD";

        User user = userDAO.findById(userId).orElse(null);
        if (user == null) return "INVALID";

        // ✅ Convert role to ENUM
        Role role;
        try {
            role = Role.valueOf(user.getRole().toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid role in DB");
        }

        // ✅ CREATE SESSION (IMPORTANT)
        return sessionManager.createSession(userId, role);
    }

    // =========================
    // RESET PASSWORD
    // =========================
    public String resetPassword(String userId, String newPassword) {

        UserLogin login = loginDAO.findById(userId).orElse(null);
        if (login == null) return "User Not Found";

        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hashPassword(newPassword, salt);

        login.setSalt(salt);
        login.setPasswordHash(hash);
        login.setFirstLogin(false);

        loginDAO.save(login);

        return "Password Updated";
    }
}

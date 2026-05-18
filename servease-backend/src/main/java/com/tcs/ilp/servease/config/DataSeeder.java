package com.tcs.ilp.servease.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tcs.ilp.servease.entity.User;
import com.tcs.ilp.servease.entity.UserLogin;
import com.tcs.ilp.servease.repository.UserRepository;
import com.tcs.ilp.servease.repository.UserLoginRepository;
import com.tcs.ilp.servease.util.PasswordUtil;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public void run(String... args) {

        // ✅ Seed Admin
        seedUser("admin1", "Admin User", "admin@servease.com", "9000000001", "ADMIN", "Admin@123");

        // ✅ Seed Supervisor
        seedUser("sup1", "Supervisor User", "supervisor@servease.com", "9000000002", "SUPERVISOR", "Super@123");

        // ✅ Seed Technician
        seedUser("tech1", "Technician User", "technician@servease.com", "9000000003", "TECHNICIAN", "Tech@123");

        // ✅ Seed Customer
        seedUser("cust1", "Customer User", "customer@servease.com", "9000000004", "CUSTOMER", "Cust@123");

        System.out.println("✅ DataSeeder: Default users seeded (if not already existing)");
    }

    private void seedUser(String userId, String name, String email, String phone, String role, String password) {

        if (userRepository.existsById(userId)) {
            return; // already exists, skip
        }

        // Create User
        User user = new User(userId, name, email, phone, role);
        userRepository.save(user);

        // Create UserLogin with hashed password
        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hashPassword(password, salt);
        UserLogin login = new UserLogin(userId, hash, salt, false);
        userLoginRepository.save(login);

        System.out.println("  → Seeded user: " + userId + " (" + role + ")");
    }
}

package com.tcs.ilp.servease.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

    // =========================
    // GENERATE SALT 
    // =========================
    public static String generateSalt() {
        try {
            byte[] salt = new byte[16];
            SecureRandom sr = new SecureRandom();
            sr.nextBytes(salt);
            return Base64.getEncoder().encodeToString(salt);
        } catch (Exception e) {
            throw new RuntimeException("Error generating salt", e);
        }
    }

    // =========================
    // HASH PASSWORD 
    // =========================
    public static String hashPassword(String password, String salt) {

        try {
            String salted = password + salt;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(salted.getBytes());

            return Base64.getEncoder().encodeToString(hashedBytes);

        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}

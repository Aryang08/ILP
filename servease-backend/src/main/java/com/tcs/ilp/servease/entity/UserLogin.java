package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_login", schema = "dev")
public class UserLogin {

    // =========================
    // ✅ PRIMARY KEY
    // =========================
    @Id
    @Column(name = "user_id")
    private String userId;

    // =========================
    // ✅ AUTH FIELDS
    // =========================
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "salt", length = 255)
    private String salt;

    @Column(name = "first_login")
    private boolean firstLogin;

    // =========================
    // ✅ CONSTRUCTORS
    // =========================
    public UserLogin() {}

    public UserLogin(String userId,
                     String passwordHash,
                     String salt,
                     boolean firstLogin) {
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.firstLogin = firstLogin;
    }

    // =========================
    // ✅ GETTERS & SETTERS
    // =========================
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
}
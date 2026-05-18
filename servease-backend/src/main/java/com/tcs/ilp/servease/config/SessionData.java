package com.tcs.ilp.servease.config;

public class SessionData {

    private String userId;
    private Role role;

    // ✅ Default constructor (important)
    public SessionData() {
    }

    // ✅ Correct constructor
    public SessionData(String userId, Role role) {
        this.userId = userId;
        this.role = role;
    }

    // ✅ Getter
    public String getUserId() {
        return userId;
    }

    public Role getRole() {
        return role;
    }

    // ✅ Setters
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

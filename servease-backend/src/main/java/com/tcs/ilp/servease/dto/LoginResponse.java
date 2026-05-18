package com.tcs.ilp.servease.dto;

import com.tcs.ilp.servease.config.Role;

public class LoginResponse {

    private String sessionId;
    private Role role;

    public LoginResponse(String sessionId, Role role) {
        this.sessionId = sessionId;
        this.role = role;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Role getRole() {
        return role;
    }
}

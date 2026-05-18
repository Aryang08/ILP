package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "user", schema = "dev")
public class User {

    // =========================
    //  PRIMARY KEY
    // =========================
    @Id
    @Column(name = "user_id")
    private String userId;

    // =========================
    //  OTHER FIELDS
    // =========================
    @Schema(example = "John Doe")
    @Column(name = "name", nullable = false)
    private String name;

    @Schema(example = "john@example.com")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Schema(example = "9876543210")
    @Column(name = "phone", length = 15)
    private String phone;

    //  VERY IMPORTANT FOR LOGIN SYSTEM
    @Schema(example = "SUPERVISOR")
    @Column(name = "role")
    private String role;

    // =========================
    //  CONSTRUCTORS
    // =========================
    public User() {}

    public User(String userId, String name, String email, String phone, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    // =========================
    //  GETTERS & SETTERS
    // =========================
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

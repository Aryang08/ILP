package com.tcs.ilp.servease.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity

@Table(name = "admin", schema = "dev")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "admin_id", nullable = false)
    private String adminId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    // ✅ Mandatory no-args constructor
    public Admin() {
    }

    // ✅ Parameterized constructor
    public Admin(String adminId, String userId) {
        this.adminId = adminId;
        this.userId = userId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
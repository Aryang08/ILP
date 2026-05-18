package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "technician", schema = "dev")
public class Technician {

    // =========================
    // PRIMARY KEY
    // =========================
    @Id
    @Column(name = "technician_id")
    private String technicianId;

    // =========================
    // FOREIGN KEYS
    // =========================
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "service_center_id", nullable = false)
    private String serviceCenterId;

    // =========================
    // CONSTRUCTORS
    // =========================
    public Technician() {
        // REQUIRED for JPA
    }

    public Technician(String technicianId, String userId, String serviceCenterId) {
        this.technicianId = technicianId;
        this.userId = userId;
        this.serviceCenterId = serviceCenterId;
    }

    // =========================
    // GETTERS & SETTERS
    // =========================
    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(String serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }
}
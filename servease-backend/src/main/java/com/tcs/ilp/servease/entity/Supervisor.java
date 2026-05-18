package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "supervisor", schema = "dev")
public class Supervisor {

    // =========================
    // PRIMARY KEY
    // =========================
    @Id
    @Column(name = "supervisor_id")
    private String supervisorId;

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
    public Supervisor() {
    }

    public Supervisor(String supervisorId, String userId, String serviceCenterId) {
        this.supervisorId = supervisorId;
        this.userId = userId;
        this.serviceCenterId = serviceCenterId;
    }

    // =========================
    // GETTERS & SETTERS
    // =========================
    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
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

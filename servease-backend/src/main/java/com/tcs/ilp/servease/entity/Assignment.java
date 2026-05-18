package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignment", schema = "dev")
public class Assignment {

    // =========================
    // PRIMARY KEY
    // =========================
    @Id
    @Column(name = "assignment_id")
    private String assignmentId;

    // =========================
    // FOREIGN KEYS (Keep simple for now)
    // =========================
    @Column(name = "service_id", nullable = false)
    private String serviceId;

    @Column(name = "technician_id", nullable = false)
    private String technicianId;

    @Column(name = "service_center_id", nullable = false)
    private String serviceCenterId;

    // =========================
    // OTHER FIELDS
    // =========================
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "status")
    private String status;

    // =========================
    // CONSTRUCTORS
    // =========================
    public Assignment() {
    }

    public Assignment(String assignmentId, String serviceId,
                      String technicianId, String serviceCenterId,
                      LocalDateTime completedAt, String status) {
        this.assignmentId = assignmentId;
        this.serviceId = serviceId;
        this.technicianId = technicianId;
        this.serviceCenterId = serviceCenterId;
        this.completedAt = completedAt;
        this.status = status;
    }

    // =========================
    // GETTERS & SETTERS
    // =========================
    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(String serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

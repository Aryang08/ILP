package com.tcs.ilp.servease.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public class AssignmentDTO {
//	@NotBlank
    private String serviceId;
    
    @NotBlank
    private String technicianId;

    // REMOVE or IGNORE (set by backend)
    private String serviceCenterId;

    private LocalDateTime completedAt;
    
    @Pattern(regexp = "ASSIGNED|IN_PROGRESS|COMPLETED|DELAYED")
    private String status;

    public AssignmentDTO() {}

    // =========================
    // GETTERS & SETTERS
    // =========================

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
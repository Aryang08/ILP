package com.tcs.ilp.servease.dto;

public class TechnicianDTO {

    private String technicianId;
    private String userId;

    // Optional: can be removed if always set by backend
    private String serviceCenterId;

    public TechnicianDTO() {}

    public TechnicianDTO(String technicianId, String userId, String serviceCenterId) {
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
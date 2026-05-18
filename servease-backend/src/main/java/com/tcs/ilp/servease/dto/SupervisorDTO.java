package com.tcs.ilp.servease.dto;

import jakarta.validation.constraints.NotBlank;

public class SupervisorDTO {

    @NotBlank(message = "Supervisor ID is required")
    private String supervisorId;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Service Center ID is required")
    private String serviceCenterId;

    public SupervisorDTO() {}

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
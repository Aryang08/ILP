package com.tcs.ilp.servease.dto;

import java.time.LocalDate;

public class TodayScheduleDTO {

    private String serviceId;
    private String technicianId;
    private LocalDate scheduledDate;
    private String status;
    public TodayScheduleDTO() {}

    public TodayScheduleDTO(String serviceId, String technicianId,
                            LocalDate scheduledDate, String status) {
        this.serviceId = serviceId;
        this.technicianId = technicianId;
        this.scheduledDate = scheduledDate;
        this.status = status;
    }

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public String getTechnicianId() { return technicianId; }
    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public LocalDate getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
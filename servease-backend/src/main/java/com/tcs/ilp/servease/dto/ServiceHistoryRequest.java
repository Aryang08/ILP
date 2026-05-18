package com.tcs.ilp.servease.dto;

import java.time.LocalDateTime;

public class ServiceHistoryRequest {

    private String historyId;
    private String serviceId;
    private String technicianId;
    private LocalDateTime reopenDate;

    // Getters & Setters
    public String getHistoryId() { return historyId; }
    public void setHistoryId(String historyId) { this.historyId = historyId; }

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public String getTechnicianId() { return technicianId; }
    public void setTechnicianId(String technicianId) { this.technicianId = technicianId; }

    public LocalDateTime getReopenDate() { return reopenDate; }
    public void setReopenDate(LocalDateTime reopenDate) { this.reopenDate = reopenDate; }
}
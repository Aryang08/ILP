package com.tcs.ilp.servease.dto;

import java.time.LocalDateTime;

public class HistoryRecordDTO {

    private String eventType;
    private String description;
    private LocalDateTime eventTime;

    //  REQUIRED DEFAULT CONSTRUCTOR
    public HistoryRecordDTO() {}

    //  REQUIRED FOR JPQL PROJECTION (VERY IMPORTANT)
    public HistoryRecordDTO(String eventType, String description, LocalDateTime eventTime) {
        this.eventType = eventType;
        this.description = description;
        this.eventTime = eventTime;
    }

    // =========================
    // GETTERS & SETTERS
    // =========================
    public String getEventType() { return eventType; }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEventTime() { return eventTime; }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }
}

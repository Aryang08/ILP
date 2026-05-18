package com.tcs.ilp.servease.dto;

import java.util.List;

public class SupervisorHistoryDTO {

    private long totalActivity;
    private long ticketsAssigned;
    private long statusUpdates;
    private long escalations;
    private long completed;
    
    public SupervisorHistoryDTO() {}

    public SupervisorHistoryDTO(long totalActivity, long ticketsAssigned,
                                long statusUpdates, long escalations,
                                long completed, List<HistoryRecordDTO> history) {
        this.totalActivity = totalActivity;
        this.ticketsAssigned = ticketsAssigned;
        this.statusUpdates = statusUpdates;
        this.escalations = escalations;
        this.completed = completed;
        this.history = history;
    }

    private List<HistoryRecordDTO> history;

    public long getTotalActivity() { return totalActivity; }
    public void setTotalActivity(long totalActivity) {
        this.totalActivity = totalActivity;
    }

    public long getTicketsAssigned() { return ticketsAssigned; }
    public void setTicketsAssigned(long ticketsAssigned) {
        this.ticketsAssigned = ticketsAssigned;
    }

    public long getStatusUpdates() { return statusUpdates; }
    public void setStatusUpdates(long statusUpdates) {
        this.statusUpdates = statusUpdates;
    }

    public long getEscalations() { return escalations; }
    public void setEscalations(long escalations) {
        this.escalations = escalations;
    }

    public long getCompleted() { return completed; }
    public void setCompleted(long completed) {
        this.completed = completed;
    }

    public List<HistoryRecordDTO> getHistory() { return history; }
    public void setHistory(List<HistoryRecordDTO> history) {
        this.history = history;
    }
}
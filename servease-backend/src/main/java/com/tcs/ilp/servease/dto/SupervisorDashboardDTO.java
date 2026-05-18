package com.tcs.ilp.servease.dto;

import java.util.List;

public class SupervisorDashboardDTO {

    private long totalOpen;
    private long pendingAssignment;
    private long inProgress;
    private long delayed;
    
    public SupervisorDashboardDTO() {}
    private List<TechnicianAvailabilityDTO> technicianAvailability;
    private List<TodayScheduleDTO> todaysSchedule;

    public long getTotalOpen() { return totalOpen; }
    public void setTotalOpen(long totalOpen) { this.totalOpen = totalOpen; }

    public long getPendingAssignment() { return pendingAssignment; }
    public void setPendingAssignment(long pendingAssignment) {
        this.pendingAssignment = pendingAssignment;
    }

    public long getInProgress() { return inProgress; }
    public void setInProgress(long inProgress) { this.inProgress = inProgress; }

    public long getDelayed() { return delayed; }
    public void setDelayed(long delayed) { this.delayed = delayed; }

    public List<TechnicianAvailabilityDTO> getTechnicianAvailability() {
        return technicianAvailability;
    }
    public void setTechnicianAvailability(
            List<TechnicianAvailabilityDTO> technicianAvailability) {
        this.technicianAvailability = technicianAvailability;
    }

    public List<TodayScheduleDTO> getTodaysSchedule() {
        return todaysSchedule;
    }
    public void setTodaysSchedule(List<TodayScheduleDTO> todaysSchedule) {
        this.todaysSchedule = todaysSchedule;
    }
}
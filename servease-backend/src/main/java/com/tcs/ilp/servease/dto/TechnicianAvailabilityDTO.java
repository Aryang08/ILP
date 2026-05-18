package com.tcs.ilp.servease.dto;

public class TechnicianAvailabilityDTO {

    private String technicianId;
    private String technicianName;
    private boolean available;
    private int activeAssignments;
    
    public TechnicianAvailabilityDTO() {}

    public TechnicianAvailabilityDTO(String technicianId, String technicianName,
                                     int activeAssignments, boolean available) {
        this.technicianId = technicianId;
        this.technicianName = technicianName;
        this.activeAssignments = activeAssignments;
        this.available = available;
    }
    
    public String getTechnicianId() { return technicianId; }
    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getTechnicianName() { return technicianName; }
    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getActiveAssignments() { return activeAssignments; }
    public void setActiveAssignments(int activeAssignments) {
        this.activeAssignments = activeAssignments;
    }
}
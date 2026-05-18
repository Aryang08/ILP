package com.tcs.ilp.servease.dto;

public class TechnicianAssignmentDTO {

    private String assignmentId;
    private String serviceId;
    private String technicianName;
    private String status;

    // No-argument constructor (important for Spring/JSON)
    public TechnicianAssignmentDTO() {
    }

    // Parameterized constructor (optional but useful)
    public TechnicianAssignmentDTO(String assignmentId, String serviceId,
                                   String technicianName, String status) {
        this.assignmentId = assignmentId;
        this.serviceId = serviceId;
        this.technicianName = technicianName;
        this.status = status;
    }

    // Getters and Setters
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

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Optional (very useful for debugging/logging)
    @Override
    public String toString() {
        return "TechnicianAssignmentDTO{" +
                "assignmentId='" + assignmentId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", technicianName='" + technicianName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
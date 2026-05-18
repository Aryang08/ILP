package com.tcs.ilp.servease.dto;

public class AssignmentTableDTO {

    private String serviceId;
    private String technicianName;
    private String serviceCenter;
    private String status;
    private String completedAt;

    // ✅ REQUIRED for JPQL constructor projection
    public AssignmentTableDTO(
            String serviceId,
            String technicianName,
            String serviceCenter,
            String status,
            String completedAt) {

        this.serviceId = serviceId;
        this.technicianName = technicianName;
        this.serviceCenter = serviceCenter;
        this.status = status;
        this.completedAt = completedAt;
    }

    // ✅ Required by frameworks (Jackson / Hibernate if needed)
    public AssignmentTableDTO() {
    }

    // ✅ Getters
    public String getServiceId() {
        return serviceId;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public String getServiceCenter() {
        return serviceCenter;
    }

    public String getStatus() {
        return status;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    // ✅ Setters
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public void setServiceCenter(String serviceCenter) {
        this.serviceCenter = serviceCenter;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }
}
package com.tcs.ilp.servease.dto;

public class TechnicianDashboardDTO {

    private String technicianId;
    private String serviceId;
    private String applianceName;
    private String issue;
    private String status;
    private String customerName;
    private String address;
    private String time;
    private String description;

    public TechnicianDashboardDTO(String technicianId, String serviceId,
                                  String applianceName, String issue,
                                  String status, String customerName,
                                  String address, String time,
                                  String description) {

        this.technicianId = technicianId;
        this.serviceId = serviceId;
        this.applianceName = applianceName;
        this.issue = issue;
        this.status = status;
        this.customerName = customerName;
        this.address = address;
        this.time = time;
        this.description = description;
    }

    // ✅ ADD GETTERS
    public String getTechnicianId() { return technicianId; }
    public String getServiceId() { return serviceId; }
    public String getApplianceName() { return applianceName; }
    public String getIssue() { return issue; }
    public String getStatus() { return status; }
    public String getCustomerName() { return customerName; }
    public String getAddress() { return address; }
    public String getTime() { return time; }
    public String getDescription() { return description; }
}
package com.tcs.ilp.servease.dto;

public class ServiceRequestDTO {

    private String customerName;
	private String applianceName;
    private String issueDate;
    private String preferredDate;
    private String status;

    public ServiceRequestDTO() {}
    public ServiceRequestDTO(String customerName, String applianceName, String issueDate, String preferredDate,
			String status) {
		super();
		this.customerName = customerName;
		this.applianceName = applianceName;
		this.issueDate = issueDate;
		this.preferredDate = preferredDate;
		this.status = status;
	}


    // Getters
    public String getCustomerName() {
        return customerName;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getPreferredDate() {
        return preferredDate;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public void setPreferredDate(String preferredDate) {
        this.preferredDate = preferredDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
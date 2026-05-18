package com.tcs.ilp.servease.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.*;

public class ServiceTableDTO {
	@NotBlank
    private String serviceId;
	@NotBlank
    private String customerName;
	@Pattern(regexp = "OPEN|IN_PROGRESS|COMPLETED|CLOSED|REOPENED")
    private String status;
	@NotNull
    private LocalDate createdDate;

    //  REQUIRED DEFAULT CONSTRUCTOR
    public ServiceTableDTO() {}

    //  REQUIRED FOR JPQL PROJECTION (VERY IMPORTANT)
    public ServiceTableDTO(String serviceId, String customerName, String status) {
        this.serviceId = serviceId;
        this.customerName = customerName;
        this.status = status;
    }

    // =========================
    // GETTERS & SETTERS
    // =========================
    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
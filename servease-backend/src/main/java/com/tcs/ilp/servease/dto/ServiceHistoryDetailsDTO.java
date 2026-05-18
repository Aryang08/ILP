package com.tcs.ilp.servease.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ServiceHistoryDetailsDTO {

    private String serviceId;
    private String applianceName;
    private String status;
    private String issueDescription;
    private String technicianName;
    private LocalDateTime completedAt;
    private BigDecimal billAmount;

    public ServiceHistoryDetailsDTO(
            String serviceId,
            String applianceName,
            String status,
            String issueDescription,
            String technicianName,
            LocalDateTime completedAt,
            BigDecimal billAmount) {

        this.serviceId = serviceId;
        this.applianceName = applianceName;
        this.status = status;
        this.issueDescription = issueDescription;
        this.technicianName = technicianName;
        this.completedAt = completedAt;
        this.billAmount = billAmount;
    }

    @Override
    public String toString() {
        return """
            Appliance      : %s
            Service ID     : %s
            Status         : %s
            Issue          : %s
            Technician     : %s
            Completed At   : %s
            Billing Amount : ₹%s
            """.formatted(
                applianceName,
                serviceId,
                status,
                issueDescription,
                technicianName,
                completedAt,
                billAmount
        );
    }
}
package com.tcs.ilp.servease.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;

public class ServiceHistoryDTO {

    private String historyId;
    private String serviceId;
    private String technicianId;
    private String technicianName;
    private String applianceName;
    private String billId;
    private BigDecimal billAmount;
    private LocalDateTime reopenDate;

    public ServiceHistoryDTO(String historyId, String serviceId, String technicianId,
                             String technicianName, String applianceName,
                             String billId, BigDecimal billAmount,
                             LocalDateTime reopenDate) {
        this.historyId = historyId;
        this.serviceId = serviceId;
        this.technicianId = technicianId;
        this.technicianName = technicianName;
        this.applianceName = applianceName;
        this.billId = billId;
        this.billAmount = billAmount;
        this.reopenDate = reopenDate;
    }

    // ✅ Getters
    public String getHistoryId() { return historyId; }
    public String getServiceId() { return serviceId; }
    public String getTechnicianId() { return technicianId; }
    public String getTechnicianName() { return technicianName; }
    public String getApplianceName() { return applianceName; }
    public String getBillId() { return billId; }
    public BigDecimal getBillAmount() { return billAmount; }
    public LocalDateTime getReopenDate() { return reopenDate; }
}
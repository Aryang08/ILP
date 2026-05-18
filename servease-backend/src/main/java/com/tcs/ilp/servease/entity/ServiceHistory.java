package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "service_history", schema = "dev")
public class ServiceHistory {

    // =========================
    //  PRIMARY KEY
    // =========================
    @Id
    @Column(name = "history_id")
    private String historyId;

    // =========================
    //  TABLE FIELDS
    // =========================
    @Column(name = "service_id")
    private String serviceId;

    @Column(name = "technician_id")
    private String technicianId;

    @Column(name = "reopen_date")
    private LocalDateTime reopenDate;

    // =========================
    //  TRANSIENT FIELDS (JOIN / DTO USE)
    // =========================
    @Transient
    private String technicianName;

    @Transient
    private String applianceName;

    @Transient
    private String billId;

    @Transient
    private BigDecimal billAmount;

    // =========================
    //  CONSTRUCTORS
    // =========================
    public ServiceHistory() {}

    public ServiceHistory(String historyId,
                          String serviceId,
                          String technicianId,
                          LocalDateTime reopenDate) {
        this.historyId = historyId;
        this.serviceId = serviceId;
        this.technicianId = technicianId;
        this.reopenDate = reopenDate;
    }

    public ServiceHistory(String historyId,
                          String serviceId,
                          String technicianId,
                          String technicianName,
                          String applianceName,
                          String billId,
                          BigDecimal billAmount,
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

    // =========================
    //  GETTERS
    // =========================
    public String getHistoryId() { return historyId; }
    public String getServiceId() { return serviceId; }
    public String getTechnicianId() { return technicianId; }
    public String getTechnicianName() { return technicianName; }
    public String getApplianceName() { return applianceName; }
    public String getBillId() { return billId; }
    public BigDecimal getBillAmount() { return billAmount; }
    public LocalDateTime getReopenDate() { return reopenDate; }

    // =========================
    //  SETTERS (JPA REQUIRED)
    // =========================
    public void setHistoryId(String historyId) { this.historyId = historyId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }
    public void setTechnicianId(String technicianId) { this.technicianId = technicianId; }
    public void setReopenDate(LocalDateTime reopenDate) { this.reopenDate = reopenDate; }

    public void setTechnicianName(String technicianName) { this.technicianName = technicianName; }
    public void setApplianceName(String applianceName) { this.applianceName = applianceName; }
    public void setBillId(String billId) { this.billId = billId; }
    public void setBillAmount(BigDecimal billAmount) { this.billAmount = billAmount; }

    // =========================
    //  DEBUG / LOGGING
    // =========================
    @Override
    public String toString() {
        return "ServiceHistory{" +
                "historyId='" + historyId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", technicianName='" + technicianName + '\'' +
                ", applianceName='" + applianceName + '\'' +
                ", billId='" + billId + '\'' +
                ", billAmount=" + billAmount +
                ", reopenDate=" + reopenDate +
                '}';
    }
}
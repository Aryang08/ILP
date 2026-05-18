package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "service", schema = "dev")
public class ServiceMain {

    // =========================
    //  PRIMARY KEY
    // =========================
    @Id
    @Column(name = "service_id")
    private String serviceId;

    // =========================
    //  FOREIGN KEYS
    // =========================
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "service_center_id", nullable = false)
    private String serviceCenterId;

    @Column(name = "appliance_id")
    private String applianceId;

    // =========================
    //  DATE FIELDS
    // =========================
    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "preferred_date")
    private LocalDate preferredDate;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // =========================
    //  OTHER FIELDS
    // =========================
    @Column(name = "reopen_count")
    private int reopenCount;

    @Column(name = "status")
    private String status;

    @Column(name = "issue_summary")
    private String issueSummary;

    //  IMPORTANT FIX: unify column name
    @Column(name = "custom_complaint") 
    private String customComplaint;

    // =========================
    //  CONSTRUCTORS
    // =========================
    public ServiceMain() {
    }

    public ServiceMain(String serviceId, String customerId, String serviceCenterId,
                   String applianceId, LocalDate issueDate,
                   LocalDate preferredDate, int reopenCount,
                   String status, String issueSummary,
                   LocalDateTime createdAt, String customComplaint) {

        this.serviceId = serviceId;
        this.customerId = customerId;
        this.serviceCenterId = serviceCenterId;
        this.applianceId = applianceId;
        this.issueDate = issueDate;
        this.preferredDate = preferredDate;
        this.reopenCount = reopenCount;
        this.status = status;
        this.issueSummary = issueSummary;
        this.createdAt = createdAt;
        this.customComplaint = customComplaint;
    }

    // =========================
    //  GETTERS & SETTERS
    // =========================
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(String serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public String getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(LocalDate preferredDate) {
        this.preferredDate = preferredDate;
    }

    public int getReopenCount() {
        return reopenCount;
    }

    public void setReopenCount(int reopenCount) {
        this.reopenCount = reopenCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIssueSummary() {
        return issueSummary;
    }

    public void setIssueSummary(String issueSummary) {
        this.issueSummary = issueSummary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCustomComplaint() {
        return customComplaint;
    }

    public void setCustomComplaint(String customComplaint) {
        this.customComplaint = customComplaint;
    }
}
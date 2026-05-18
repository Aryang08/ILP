package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "appliance", schema = "dev")
public class Appliance {

    @Id
    @Column(name = "appliance_id")
    private String applianceId;

    private String customerId;
    private String serialNo;
    private String name;
    private String modelNo;
    private LocalDate purchaseDate;
    private String warrantyStatus;
    private LocalDate installationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EntityStatus status = EntityStatus.ACTIVE;

    public Appliance() {}

    public Appliance(String applianceId, String customerId, String serialNo,
                     String name, String modelNo, LocalDate purchaseDate,
                     String warrantyStatus, LocalDate installationDate) {
        this.applianceId = applianceId;
        this.customerId = customerId;
        this.serialNo = serialNo;
        this.name = name;
        this.modelNo = modelNo;
        this.purchaseDate = purchaseDate;
        this.warrantyStatus = warrantyStatus;
        this.installationDate = installationDate;
        this.status = EntityStatus.ACTIVE;
    }

    // ✅ GETTERS

    public String getApplianceId() {
        return applianceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public String getName() {
        return name;
    }

    public String getModelNo() {
        return modelNo;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public String getWarrantyStatus() {
        return warrantyStatus;
    }

    public LocalDate getInstallationDate() {
        return installationDate;
    }

    // ✅ SETTERS

    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setWarrantyStatus(String warrantyStatus) {
        this.warrantyStatus = warrantyStatus;
    }

    public void setInstallationDate(LocalDate installationDate) {
        this.installationDate = installationDate;
        this.status = EntityStatus.ACTIVE;
    }
}
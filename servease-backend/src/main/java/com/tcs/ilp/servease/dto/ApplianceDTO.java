package com.tcs.ilp.servease.dto;

import java.time.LocalDate;

public class ApplianceDTO {

    private String applianceId;
    private String customerId;
    private String serialNo;
    private String name;
    private String modelNo;
    private LocalDate purchaseDate;
    private String warrantyStatus;
    private LocalDate installationDate;

    // ✅ Default Constructor
    public ApplianceDTO() {
    }

    // ✅ Parameterized Constructor
    public ApplianceDTO(String applianceId, String customerId, String serialNo,
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
    }

    // ✅ Getters and Setters

    public String getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getWarrantyStatus() {
        return warrantyStatus;
    }

    public void setWarrantyStatus(String warrantyStatus) {
        this.warrantyStatus = warrantyStatus;
    }

    public LocalDate getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(LocalDate installationDate) {
        this.installationDate = installationDate;
    }
}
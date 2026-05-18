package com.tcs.ilp.servease.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BillUserDTO {

    private String assignmentId;
    private String applianceName;
    private BigDecimal amount;
    private LocalDate billDate;

    public BillUserDTO() {}

    public BillUserDTO(String assignmentId,
                       String applianceName,
                       BigDecimal amount,
                       LocalDate billDate) {
        this.assignmentId = assignmentId;
        this.applianceName = applianceName;
        this.amount = amount;
        this.billDate = billDate;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }
}
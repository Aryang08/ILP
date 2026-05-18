package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bill")
public class Bill {

    @Id
    private String billId;

    private String assignmentId;
    private BigDecimal amount;
    private LocalDate billDate;

    public Bill() {}

    public Bill(String billId, String assignmentId,
                BigDecimal amount, LocalDate billDate) {
        this.billId = billId;
        this.assignmentId = assignmentId;
        this.amount = amount;
        this.billDate = billDate;
    }

    // ✅ GETTERS

    public String getBillId() {
        return billId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    // ✅ SETTERS (IMPORTANT)

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }
}
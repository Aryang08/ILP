package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "bill_user", schema = "dev")
public class BillUser {

    @Id
    private String billUserId;

    private String assignmentId;
    private BigDecimal amount;
    private LocalDate billUserDate;

    public BillUser() {}

    public BillUser(String billUserId, String assignmentId,
                    BigDecimal amount, LocalDate billUserDate) {
        this.billUserId = billUserId;
        this.assignmentId = assignmentId;
        this.amount = amount;
        this.billUserDate = billUserDate;
    }

    // ✅ GETTERS

    public String getBillUserId() {
        return billUserId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getBillUserDate() {
        return billUserDate;
    }

    // ✅ SETTERS

    public void setBillUserId(String billUserId) {
        this.billUserId = billUserId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setBillUserDate(LocalDate billUserDate) {
        this.billUserDate = billUserDate;
    }
}
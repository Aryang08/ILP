package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_login", schema = "dev")
public class Customer {

    // =========================
    // ✅ PRIMARY KEY
    // =========================
    @Id
    @Column(name = "customer_id")
    private String customerId;

    // =========================
    // ✅ FOREIGN KEY
    // =========================
    @Column(name = "user_id", nullable = false)
    private String userId;

    // =========================
    // ✅ OTHER FIELDS
    // =========================
    @Column(name = "address")
    private String address;

    @Column(name = "pincode")
    private String pincode;

    // =========================
    // ✅ CONSTRUCTORS
    // =========================
    public Customer() {}

    public Customer(
            String customerId,
            String userId,
            String address,
            String pincode
    ) {
        this.customerId = customerId;
        this.userId = userId;
        this.address = address;
        this.pincode = pincode;
    }

    // =========================
    // ✅ GETTERS & SETTERS
    // =========================
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}

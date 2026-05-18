package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "service_center", schema = "dev")
public class ServiceCenter {

    // =========================
    // PRIMARY KEY
    // =========================
    @Id
    @Column(name = "service_center_id")
    private String scId;

    // =========================
    // COMMON FIELDS
    // =========================
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "phone")
    private String phone;

    // =========================
    // ADDITIONAL FIELD (MERGED)
    // =========================
    @Column(name = "service_center_name")
    private String serviceCenterName;

    // =========================
    // CONSTRUCTORS
    // =========================
    public ServiceCenter() {}

    public ServiceCenter(String scId, String name, String address,
                         String pincode, String phone, String serviceCenterName) {
        this.scId = scId;
        this.name = name;
        this.address = address;
        this.pincode = pincode;
        this.phone = phone;
        this.serviceCenterName = serviceCenterName;
    }

    // =========================
    // GETTERS & SETTERS
    // =========================

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getServiceCenterName() {
        return serviceCenterName;
    }

    public void setServiceCenterName(String serviceCenterName) {
        this.serviceCenterName = serviceCenterName;
    }
}

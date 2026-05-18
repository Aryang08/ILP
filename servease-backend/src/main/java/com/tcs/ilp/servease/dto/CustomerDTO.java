package com.tcs.ilp.servease.dto;
import jakarta.validation.constraints.*;
public class CustomerDTO {
	@NotBlank
    private String customerId;
	@NotBlank
    private String userId;
	@NotBlank
    private String address;

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
}
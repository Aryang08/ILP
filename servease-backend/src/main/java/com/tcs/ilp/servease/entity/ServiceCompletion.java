package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_completion", schema = "dev")
public class ServiceCompletion {

    @Id
    @Column(name = "completion_id")
    private String completionId;

    @Column(name = "service_id")
    private String serviceId;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    //  No-arg constructor (required by JPA)
    public ServiceCompletion() {}

    //  Parameterized constructor
    public ServiceCompletion(String completionId, String serviceId,
                             String description, LocalDateTime createdAt) {
        this.completionId = completionId;
        this.serviceId = serviceId;
        this.description = description;
        this.createdAt = createdAt;
    }

    //  Auto set createdAt if null
    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    //  GETTERS
    public String getCompletionId() {
        return completionId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    //  SETTERS (IMPORTANT )
    public void setCompletionId(String completionId) {
        this.completionId = completionId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
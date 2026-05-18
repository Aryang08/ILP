package com.tcs.ilp.servease.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "feedback", schema = "dev")
public class FeedbackUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "feedback_id", nullable = false)
    private String feedbackId;

    @Column(name = "service_id", nullable = false)
    private String serviceId;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "rating")
    private Integer rating;

    // ✅ DB column name is `comment`
    @Column(name = "comment")
    private String comments;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // ✅ Mandatory no‑arg constructor
    public FeedbackUser() {
    }

    public FeedbackUser(String feedbackId, String serviceId,
                        String customerId, Integer rating,
                        String comments, LocalDateTime createdAt) {
        this.feedbackId = feedbackId;
        this.serviceId = serviceId;
        this.customerId = customerId;
        this.rating = rating;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

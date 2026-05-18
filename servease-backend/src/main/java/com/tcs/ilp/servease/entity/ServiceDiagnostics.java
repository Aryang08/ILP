package com.tcs.ilp.servease.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "service_diagnostics", schema = "dev")
public class ServiceDiagnostics {

    @Id
    @Column(name = "diagnostic_id")
    private String diagnosticId;

    @Column(name = "service_id", nullable = false)
    private String serviceId;

    @Column(name = "question_id", nullable = false)
    private String questionId;

    @Column(name = "option_id", nullable = false)
    private String selectedOptionId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ServiceDiagnostics() {
        this.createdAt = LocalDateTime.now();
    }

    public ServiceDiagnostics(String diagnosticId, String serviceId,
                              String questionId, String selectedOptionId) {
        this.diagnosticId = diagnosticId;
        this.serviceId = serviceId;
        this.questionId = questionId;
        this.selectedOptionId = selectedOptionId;
        this.createdAt = LocalDateTime.now();
    }

    public String getServiceDiagnosticId() {
        return diagnosticId;
    }

    public void setServiceDiagnosticId(String diagnosticId) {
        this.diagnosticId = diagnosticId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getSelectedOptionId() {
        return selectedOptionId;
    }

    public void setSelectedOptionId(String selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
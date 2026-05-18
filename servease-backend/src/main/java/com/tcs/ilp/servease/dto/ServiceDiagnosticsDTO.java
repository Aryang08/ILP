package com.tcs.ilp.servease.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Service Diagnostics.
 * Used to move diagnostic data across layers
 * without exposing persistence objects.
 */
public class ServiceDiagnosticsDTO {

    private String diagnosticId;
    private String serviceId;
    private String questionId;
    private String selectedOptionId;
    private LocalDateTime answeredAt;

    public String getDiagnosticId() {
        return diagnosticId;
    }

    public void setDiagnosticId(String diagnosticId) {
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

    public LocalDateTime getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(LocalDateTime answeredAt) {
        this.answeredAt = answeredAt;
    }
}
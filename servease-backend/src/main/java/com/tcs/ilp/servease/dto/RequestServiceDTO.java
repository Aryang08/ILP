package com.tcs.ilp.servease.dto;

import java.util.List;

public class RequestServiceDTO {

    private String serviceId;
    private String customerId;
    private String applianceId;
    private String preferredDate;
    private String customComplaint;

    private List<ServiceDiagnosticsDTO> diagnostics;

    public RequestServiceDTO() {
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

    public String getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }

    public String getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(String preferredDate) {
        this.preferredDate = preferredDate;
    }

    public String getCustomComplaint() {
        return customComplaint;
    }

    public void setCustomComplaint(String customComplaint) {
        this.customComplaint = customComplaint;
    }

    public List<ServiceDiagnosticsDTO> getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(List<ServiceDiagnosticsDTO> diagnostics) {
        this.diagnostics = diagnostics;
    }
}


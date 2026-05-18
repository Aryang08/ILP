package com.tcs.ilp.servease.dto;

public class DashboardStatsDTO {

    private int totalCustomers;
    private int totalTechnicians;
    private int totalServiceRequests;
    private int pendingServices;
    private int inProgressServices;
    private int completedServices;

    public DashboardStatsDTO() {
    }

    public DashboardStatsDTO(int totalCustomers,
                             int totalTechnicians,
                             int totalServiceRequests,
                             int pendingServices,
                             int inProgressServices,
                             int completedServices) {

        this.totalCustomers = totalCustomers;
        this.totalTechnicians = totalTechnicians;
        this.totalServiceRequests = totalServiceRequests;
        this.pendingServices = pendingServices;
        this.inProgressServices = inProgressServices;
        this.completedServices = completedServices;
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(int totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public int getTotalTechnicians() {
        return totalTechnicians;
    }

    public void setTotalTechnicians(int totalTechnicians) {
        this.totalTechnicians = totalTechnicians;
    }

    public int getTotalServiceRequests() {
        return totalServiceRequests;
    }

    public void setTotalServiceRequests(int totalServiceRequests) {
        this.totalServiceRequests = totalServiceRequests;
    }

    public int getPendingServices() {
        return pendingServices;
    }

    public void setPendingServices(int pendingServices) {
        this.pendingServices = pendingServices;
    }

    public int getInProgressServices() {
        return inProgressServices;
    }

    public void setInProgressServices(int inProgressServices) {
        this.inProgressServices = inProgressServices;
    }

    public int getCompletedServices() {
        return completedServices;
    }

    public void setCompletedServices(int completedServices) {
        this.completedServices = completedServices;
    }
}
package com.tcs.ilp.servease.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.ilp.servease.repository.DashboardStatsRepository;

@Service
public class DashboardStatsBO {

    @Autowired
    private DashboardStatsRepository repository;

    public int getTotalCustomers() {
        return repository.getTotalCustomers();
    }

    public int getTotalTechnicians() {
        return repository.getTotalTechnicians();
    }

    public int getTotalServiceRequests() {
        return repository.getTotalServiceRequests();
    }

    public int getPendingServices() {
        return repository.getPendingServices();
    }

    public int getInProgressServices() {
        return repository.getInProgressServices();
    }

    public int getCompletedServices() {
        return repository.getCompletedServices();
    }
}
package com.tcs.ilp.servease.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardStatsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private int getCount(String sql) {
        Number count = (Number) entityManager
                .createNativeQuery(sql)
                .getSingleResult();
        return count != null ? count.intValue() : 0;
    }

    public int getTotalCustomers() {
        return getCount("SELECT COUNT(*) FROM dev.customer_login");
    }

    public int getTotalTechnicians() {
        return getCount("SELECT COUNT(*) FROM dev.technician");
    }

    public int getTotalServiceRequests() {
        return getCount("SELECT COUNT(*) FROM dev.\"service\"");
    }

    public int getPendingServices() {
        return getCount(
            "SELECT COUNT(*) FROM dev.\"service\" WHERE status = 'PENDING'"
        );
    }

    public int getInProgressServices() {
        return getCount(
            "SELECT COUNT(*) FROM dev.\"service\" WHERE status = 'IN_PROGRESS'"
        );
    }

    public int getCompletedServices() {
        return getCount(
            "SELECT COUNT(*) FROM dev.\"service\" WHERE status = 'COMPLETED'"
        );
    }
}
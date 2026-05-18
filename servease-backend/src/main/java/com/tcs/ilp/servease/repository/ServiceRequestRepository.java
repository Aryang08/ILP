package com.tcs.ilp.servease.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tcs.ilp.servease.dto.ServiceRequestDTO;

@Repository
public class ServiceRequestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // =========================
    // BASE SQL
    // =========================
    private static final String SQL = """
        SELECT
            u.name AS customer_name,
            a.name AS appliance_name,
            s.issue_date,
            s.preferred_date,
            s.status
        FROM dev."service" s
        JOIN dev.appliance a ON s.appliance_id = a.appliance_id
        JOIN dev.customer_login c ON s.customer_id = c.customer_id
        JOIN dev.user_login u ON c.user_id = u.user_id
        ORDER BY s.issue_date DESC
        """;

    // =========================
    // ✅ PAGINATED METHOD
    // =========================
    public List<ServiceRequestDTO> getAllServiceRequests(int page, int size) {

        int offset = page * size;

        Query query = entityManager.createNativeQuery(
                SQL + " LIMIT :size OFFSET :offset"
        );

        query.setParameter("size", size);
        query.setParameter("offset", offset);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();

        return rows.stream()
                .map(r -> new ServiceRequestDTO(
                        (String) r[0],
                        (String) r[1],
                        r[2] != null ? r[2].toString() : null,
                        r[3] != null ? r[3].toString() : null,
                        (String) r[4]
                ))
                .toList();
    }

    // =========================
    // ✅ NON-PAGINATED METHOD
    // =========================
    public List<ServiceRequestDTO> getAllServiceRequests() {

        Query query = entityManager.createNativeQuery(SQL);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();

        return rows.stream()
                .map(r -> new ServiceRequestDTO(
                        (String) r[0],
                        (String) r[1],
                        r[2] != null ? r[2].toString() : null,
                        r[3] != null ? r[3].toString() : null,
                        (String) r[4]
                ))
                .toList();
    }
}

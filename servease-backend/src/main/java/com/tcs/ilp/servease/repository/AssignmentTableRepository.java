package com.tcs.ilp.servease.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tcs.ilp.servease.dto.AssignmentTableDTO;

@Repository
public class AssignmentTableRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AssignmentTableDTO> getAssignments(int page, int size) {

        int offset = page * size;

        String sql = """
            SELECT 
                s.service_id AS serviceId,
                u.name AS technicianName,
                sc.name AS serviceCenter,
                s.status AS status,
                a.completed_at AS completedAt
            FROM dev.assignment a
            JOIN dev.service s ON a.service_id = s.service_id
            JOIN dev.technician t ON a.technician_id = t.technician_id
            JOIN dev.user u ON t.user_id = u.user_id
            JOIN dev.service_center sc ON s.service_center_id = sc.service_center_id
            ORDER BY s.service_id DESC
            LIMIT :size OFFSET :offset
        """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("size", size);
        query.setParameter("offset", offset);

        @SuppressWarnings("unchecked")
        List<Object[]> rows = query.getResultList();

        return rows.stream()
                .map(r -> new AssignmentTableDTO(
                        (String) r[0],
                        (String) r[1],
                        (String) r[2],
                        (String) r[3],
                        r[4] != null ? r[4].toString() : null
                ))
                .toList();
    }
}
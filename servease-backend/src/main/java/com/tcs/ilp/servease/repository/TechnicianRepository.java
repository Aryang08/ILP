package com.tcs.ilp.servease.repository;

import com.tcs.ilp.servease.entity.Technician;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface TechnicianRepository
        extends JpaRepository<Technician, String> {

    // =========================
    //  PAGINATION (FIXED)
    // =========================
    Page<Technician> findAll(Pageable pageable);

    // =========================
    // TECHNICIAN AVAILABILITY
    // =========================
    @Query(value = """
        SELECT t.technician_id,
               t.user_id,
               COUNT(a.assignment_id) AS active_assignments
        FROM dev.technician t
        LEFT JOIN dev.assignment a
          ON t.technician_id = a.technician_id
         AND a.status <> 'COMPLETED'
        WHERE t.service_center_id = (
            SELECT s.service_center_id
            FROM dev.supervisor s
            WHERE s.supervisor_id = :supervisorId
        )
        GROUP BY t.technician_id, t.user_id
        """, nativeQuery = true)
    List<Object[]> getTechnicianAvailabilityRaw(@Param("supervisorId") String supervisorId);

    // =========================
    // COUNT TECHNICIANS
    // =========================
    long countByServiceCenterId(String serviceCenterId);

    // =========================
    //  PAGINATED DASHBOARD QUERY
    // =========================
    @Query(value = """
    	    SELECT t.technician_id, u.name, COUNT(a.assignment_id)
    	    FROM dev.technician t

    	    JOIN dev.user u 
    	      ON t.user_id = u.user_id

    	    LEFT JOIN dev.assignment a 
    	      ON t.technician_id = a.technician_id 
    	     AND a.status <> 'COMPLETED'

    	    WHERE t.service_center_id = (
    	        SELECT s.service_center_id 
    	        FROM dev.supervisor s 
    	        WHERE s.supervisor_id = :supervisorId
    	    )

    	    GROUP BY t.technician_id, u.name
    	    LIMIT :limit OFFSET :offset
    	    """, nativeQuery = true)
    	List<Object[]> getTechnicianAvailabilityRawPaginated(
    	        @Param("supervisorId") String supervisorId,
    	        @Param("limit") int limit,
    	        @Param("offset") int offset
    	);
}

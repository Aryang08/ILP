package com.tcs.ilp.servease.repository;

import com.tcs.ilp.servease.entity.Assignment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository 
        extends JpaRepository<Assignment, String> {

	
    // =========================
    // COUNTS
    // =========================
    long countByStatus(String status);

    @Query(value = """
        SELECT COUNT(*) FROM dev.service s
        WHERE NOT EXISTS (
            SELECT 1 FROM dev.assignment a 
            WHERE a.service_id = s.service_id
        )
        """, nativeQuery = true)
    long countUnassigned();

    @Query(value = """
        SELECT COUNT(*) 
        FROM dev.assignment a 
        JOIN dev.service s ON a.service_id = s.service_id
        WHERE a.status <> 'COMPLETED'
        AND s.preferred_date < CURRENT_DATE
        """, nativeQuery = true)
    long countDelayed();

    // =========================
    // RELATION METHODS
    // =========================

    List<Assignment> findByServiceId(String serviceId);

    List<Assignment> findByTechnicianId(String technicianId);

    List<Assignment> findByServiceCenterId(String serviceCenterId);

    boolean existsByServiceId(String serviceId);

    long countByTechnicianIdAndStatusNot(String technicianId, String status);

    // =========================
    //  PAGINATION METHODS (NEW)
    // =========================

    Page<Assignment> findByServiceCenterId(String serviceCenterId, Pageable pageable);

    Page<Assignment> findByTechnicianId(String technicianId, Pageable pageable);

    Page<Assignment> findAll(Pageable pageable);

    // =========================
    // ASSIGNMENT TABLE DTO
    // =========================
    @Query(value = """
        SELECT a.assignment_id, a.service_id, 
               a.technician_id, a.status
        FROM dev.assignment a
        WHERE a.service_center_id = (
            SELECT s.service_center_id 
            FROM dev.supervisor s 
            WHERE s.supervisor_id = :supervisorId
        )
        """, nativeQuery = true)
    List<Object[]> getAssignmentTableRaw(@Param("supervisorId") String supervisorId);

    //  ACTIVE CHECK (duplicate but useful)
    @Query("""
        SELECT COUNT(a)
        FROM Assignment a
        WHERE a.technicianId = :technicianId
        AND a.status <> 'COMPLETED'
    """)
    long countActiveAssignments(@Param("technicianId") String technicianId);

    // =========================
    // ID GENERATION
    // =========================
    @Query(value = "SELECT assignment_id FROM dev.assignment ORDER BY assignment_id DESC LIMIT 1", nativeQuery = true)
    String findLastAssignmentId();

    // =========================
    // TODAY SCHEDULE
    // =========================
    @Query(value = """
        SELECT a.service_id, a.technician_id, 
               CURRENT_DATE AS scheduled_date, a.status
        FROM dev.assignment a
        WHERE a.service_center_id = (
            SELECT s.service_center_id 
            FROM dev.supervisor s 
            WHERE s.supervisor_id = :supervisorId
        )
        """, nativeQuery = true)
    List<Object[]> getTodayScheduleRaw(@Param("supervisorId") String supervisorId);
    
    @Query(value = """
    	    SELECT a.service_id, a.technician_id, 
    	           CURRENT_DATE AS scheduled_date, a.status
    	    FROM dev.assignment a
    	    WHERE a.service_center_id = (
    	        SELECT s.service_center_id 
    	        FROM dev.supervisor s 
    	        WHERE s.supervisor_id = :supervisorId
    	    )
    	    LIMIT :limit OFFSET :offset
    	    """, nativeQuery = true)
    	List<Object[]> getTodayScheduleRawPaginated(
    	        @Param("supervisorId") String supervisorId,
    	        @Param("limit") int limit,
    	        @Param("offset") int offset);
}

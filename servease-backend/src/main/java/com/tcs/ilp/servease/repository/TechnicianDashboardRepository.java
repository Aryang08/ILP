package com.tcs.ilp.servease.repository;

import com.tcs.ilp.servease.entity.Technician;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TechnicianDashboardRepository extends JpaRepository<Technician, String> {

    // ✅ 1. Dashboard
    @Query(value = """
        SELECT 
            a.technician_id,
            s.service_id,
            ap.name AS appliance_name,
            s.issue_summary,
            a.status,
            u.name AS customer_name,
            c.address,
            s.created_at,
            sc.description
        FROM dev.assignment a
        JOIN dev.service s ON a.service_id = s.service_id
        LEFT JOIN dev.appliance ap ON s.appliance_id = ap.appliance_id
        LEFT JOIN dev.customer_login c ON s.customer_id = c.customer_id
        LEFT JOIN dev."user" u ON c.user_id = u.user_id
        LEFT JOIN dev.service_completion sc ON s.service_id = sc.service_id
        WHERE a.technician_id = :techId
        """, nativeQuery = true)
    List<Object[]> getDashboard(@Param("techId") String techId);


    // ✅ 2. Assigned Jobs
    @Query(value = """
        SELECT 
            a.assignment_id,
            s.service_id,
            ap.name,
            s.issue_summary,
            a.status
        FROM dev.assignment a
        JOIN dev.service s ON a.service_id = s.service_id
        LEFT JOIN dev.appliance ap ON s.appliance_id = ap.appliance_id
        WHERE a.technician_id = :techId 
        AND a.status = 'ASSIGNED'
        """, nativeQuery = true)
    List<Object[]> getAssignedJobs(@Param("techId") String techId);


    // ✅ 3. Completed Jobs
    @Query(value = """
        SELECT 
            a.assignment_id,
            s.service_id,
            ap.name,
            s.issue_summary,
            a.status,
            a.completed_at
        FROM dev.assignment a
        JOIN dev.service s ON a.service_id = s.service_id
        LEFT JOIN dev.appliance ap ON s.appliance_id = ap.appliance_id
        WHERE a.technician_id = :techId 
        AND a.status = 'COMPLETED'
        """, nativeQuery = true)
    List<Object[]> getCompletedJobs(@Param("techId") String techId);


    // ✅ 4. Service History
    @Query(value = """
        SELECT 
            sh.history_id,
            s.service_id,
            ap.name,
            s.issue_summary,
            sh.reopen_date
        FROM dev.service_history sh
        JOIN dev.service s ON sh.service_id = s.service_id
        LEFT JOIN dev.appliance ap ON s.appliance_id = ap.appliance_id
        WHERE sh.technician_id = :techId
        """, nativeQuery = true)
    List<Object[]> getServiceHistory(@Param("techId") String techId);


    // ✅ 5. Service Requests
    @Query(value = """
        SELECT 
            s.service_id,
            ap.name,
            s.issue_summary,
            s.preferred_date,
            u.name,
            c.address
        FROM dev.service s
        JOIN dev.assignment a ON s.service_id = a.service_id
        LEFT JOIN dev.appliance ap ON s.appliance_id = ap.appliance_id
        LEFT JOIN dev.customer_login c ON s.customer_id = c.customer_id
        LEFT JOIN dev."user" u ON c.user_id = u.user_id
        WHERE a.technician_id = :techId
        """, nativeQuery = true)
    List<Object[]> getServiceRequests(@Param("techId") String techId);

 // ✅ INSERT DESCRIPTION
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO dev.service_completion
        (completion_id, service_id, description, created_at)
        VALUES (:completionId, :serviceId, :description, CURRENT_TIMESTAMP)
        """, nativeQuery = true)
    void addCompletion(
            @Param("completionId") String completionId,
            @Param("serviceId") String serviceId,
            @Param("description") String description
    );


    // ✅ UPDATE STATUS
    @Modifying
    @Transactional
    @Query(value = """
        UPDATE dev.assignment
        SET status = :status,
            completed_at = CURRENT_TIMESTAMP
        WHERE assignment_id = :assignmentId
        """, nativeQuery = true)
    void updateStatus(
            @Param("assignmentId") String assignmentId,
            @Param("status") String status
    );
    @Query(value = """
    	    SELECT 
    	        sh.history_id,
    	        s.service_id,
    	        ap.name,
    	        s.issue_summary,
    	        sh.reopen_date
    	    FROM dev.service_history sh
    	    JOIN dev.service s ON sh.service_id = s.service_id
    	    LEFT JOIN dev.appliance ap ON s.appliance_id = ap.appliance_id
    	    WHERE s.appliance_id = :applianceId
    	""", nativeQuery = true)
    	List<Object[]> getHistoryByAppliance(@Param("applianceId") String applianceId);
}
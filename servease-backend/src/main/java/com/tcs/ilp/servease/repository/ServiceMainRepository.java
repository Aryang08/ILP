package com.tcs.ilp.servease.repository;

import com.tcs.ilp.servease.entity.ServiceMain;
import com.tcs.ilp.servease.dto.ServiceTableDTO;
import com.tcs.ilp.servease.dto.HistoryRecordDTO;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface ServiceMainRepository 
        extends JpaRepository<ServiceMain, String> {

    // =========================
    // PAGINATION
    // =========================
    Page<ServiceMain> findAll(Pageable pageable);

    // =========================
    // COUNT BY STATUS
    // =========================
    long countByStatus(String status);

    // =========================
    // COUNT UNASSIGNED (NATIVE )
    // =========================
    @Query(value = """
        SELECT COUNT(*) FROM dev.service s
        WHERE NOT EXISTS (
            SELECT 1 FROM dev.assignment a 
            WHERE a.service_id = s.service_id
        )
        """, nativeQuery = true)
    long countUnassigned();

    // =========================
    // COUNT DELAYED (NATIVE )
    // =========================
    @Query(value = """
        SELECT COUNT(*) FROM dev.service
        WHERE status <> 'COMPLETED'
        AND preferred_date < CURRENT_DATE
        """, nativeQuery = true)
    long countDelayed();

    // =========================
    // UPDATE STATUS  FIXED
    // =========================
    @Modifying
    @Query("UPDATE ServiceMain s SET s.status = :status WHERE s.serviceId = :serviceId")
    void updateServiceStatus(@Param("serviceId") String serviceId,
                             @Param("status") String status);

    // =========================
    // ACTIVE SERVICES  FIXED
    // =========================
    @Query("""
        SELECT new com.tcs.ilp.servease.dto.ServiceTableDTO(
            s.serviceId,
            s.customerId,
            s.status
        )
        FROM ServiceMain s
        WHERE s.status <> 'COMPLETED'
        AND s.serviceCenterId = (
            SELECT sup.serviceCenterId FROM Supervisor sup
            WHERE sup.supervisorId = :supervisorId
        )
    """)
    List<ServiceTableDTO> getActiveServicesBySupervisor(
            @Param("supervisorId") String supervisorId);

    // =========================
    // SERVICE HISTORY  FIXED
    // =========================
    @Query("""
        SELECT new com.tcs.ilp.servease.dto.HistoryRecordDTO(
            s.status,
            CONCAT('Service ID: ', s.serviceId),
            s.createdAt
        )
        FROM ServiceMain s
        WHERE s.serviceId = :serviceId
    """)
    List<HistoryRecordDTO> getServiceHistory(
            @Param("serviceId") String serviceId);

    // =========================
    // FILTER METHODS
    // =========================
    List<ServiceMain> findByCustomerId(String customerId);

    List<ServiceMain> findByCustomerIdAndStatusIn(
            String customerId,
            List<String> statuses
    );

    List<ServiceMain> findByCustomerIdAndStatus(
            String customerId,
            String status
    );
}

package com.tcs.ilp.servease.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import com.tcs.ilp.servease.entity.ServiceDiagnostics;

public interface ServiceDiagnosticsRepository
        extends JpaRepository<ServiceDiagnostics, String> {

    List<ServiceDiagnostics> findByServiceId(String serviceId);

    @Query("""
        select sd.createdAt
        from ServiceDiagnostics sd
        where sd.serviceId = :serviceId
          and sd.questionId = :questionId
    """)
    LocalDateTime findAnswerTimestamp(
            @Param("serviceId") String serviceId,
            @Param("questionId") String questionId);

    @Modifying
    @Query("""
        delete from ServiceDiagnostics sd
        where sd.serviceId = :serviceId
    """)
    void deleteByServiceId(@Param("serviceId") String serviceId);
}
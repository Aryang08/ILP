package com.tcs.ilp.servease.repository;

import com.tcs.ilp.servease.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupervisorRepository 
        extends JpaRepository<Supervisor, String> {

    // =========================
    // CUSTOM METHODS
    // =========================

    // getByServiceCenterId
    List<Supervisor> findByServiceCenterId(String serviceCenterId);

    // getServiceCenterId
    Optional<Supervisor> findBySupervisorId(String supervisorId);
}

package com.tcs.ilp.servease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.ilp.servease.entity.ServiceCompletion;

@Repository
public interface ServiceCompletionRepository
        extends JpaRepository<ServiceCompletion, String> {

    // ✅ Custom query method
    List<ServiceCompletion> findByServiceId(String serviceId);
}

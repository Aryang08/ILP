package com.tcs.ilp.servease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tcs.ilp.servease.entity.Appliance;

@Repository
public interface ApplianceRepository extends JpaRepository<Appliance, String> {
	List<Appliance> findByCustomerId(String customerId);
}
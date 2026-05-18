package com.tcs.ilp.servease.repository;

import java.util.List;

import com.tcs.ilp.servease.entity.ServiceCenter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCenterRepository
        extends JpaRepository<ServiceCenter, String> {

    // =========================
    // GET BY PINCODE (FULL ENTITY)
    // =========================
    ServiceCenter findByPincode(String pincode);

    // =========================
    // GET ONLY SC ID BY PINCODE
    // =========================
    @Query("SELECT sc.scId FROM ServiceCenter sc WHERE sc.pincode = :pincode")
    String findServiceCenterIdByPincode(@Param("pincode") String pincode);

    // =========================
    // GET BY SC ID
    // =========================
    ServiceCenter findByScId(String scId);

    // =========================
    // GET ALL (optional override)
    // =========================
    List<ServiceCenter> findAll();
}
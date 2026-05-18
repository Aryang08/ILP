package com.tcs.ilp.servease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.tcs.ilp.servease.entity.Appliance;
import com.tcs.ilp.servease.entity.EntityStatus;

@Repository
public interface ApplianceRepository extends JpaRepository<Appliance, String> {
	List<Appliance> findByCustomerId(String customerId);
    List<Appliance> findByStatusNot(EntityStatus status);

    @Modifying
    @Query("update Appliance a set a.status = :status where a.applianceId = :applianceId")
    void softDeleteAppliance(@Param("applianceId") String applianceId, @Param("status") String status);

    @Modifying
    @Query("update ServiceMain s set s.status = :status where s.applianceId = :applianceId")
    void hideServicesByApplianceId(@Param("applianceId") String applianceId, @Param("status") String status);

    @Modifying
    @Query("update Assignment a set a.status = :status where a.serviceId in (select s.serviceId from ServiceMain s where s.applianceId = :applianceId)")
    void hideAssignmentsByApplianceId(@Param("applianceId") String applianceId, @Param("status") String status);

    @Modifying
    @Query(value = "update dev.service_history sh set technician_id = sh.technician_id where sh.service_id in (select s.service_id from dev.service s where s.appliance_id = :applianceId)", nativeQuery = true)
    void hideServiceHistoryByApplianceId(@Param("applianceId") String applianceId, @Param("status") String status);
}

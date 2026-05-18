package com.tcs.ilp.servease.repository;

import com.tcs.ilp.servease.entity.ServiceHistory;
import org.springframework.data.jpa.repository.*;
import java.util.List;

public interface ServiceHistoryRepository
        extends JpaRepository<ServiceHistory, String> {

    // ✅ GET ALL
    @Query(value =
        "SELECT DISTINCT ON (sh.history_id) " +
        "sh.history_id, sh.service_id, sh.technician_id, " +
        "u.name AS technician_name, " +
        "a.name AS appliance_name, " +
        "b.bill_id, b.amount, " +
        "sh.reopen_date " +

        "FROM dev.service_history sh " +

        "JOIN dev.technician t " +
        "ON sh.technician_id = t.technician_id " +

        "JOIN dev.user_login ul " +
        "ON t.user_id = ul.user_id " +

        "JOIN dev.user u " +
        "ON ul.user_id = u.user_id " +

        "JOIN dev.service s " +
        "ON sh.service_id = s.service_id " +

        "JOIN dev.appliance a " +
        "ON s.appliance_id = a.appliance_id " +

        "LEFT JOIN dev.assignment ass " +
        "ON s.service_id = ass.service_id " +

        "LEFT JOIN dev.bill b " +
        "ON ass.assignment_id = b.assignment_id " +

        "ORDER BY sh.history_id, sh.reopen_date DESC",

        nativeQuery = true)
    List<Object[]> getAllHistoryRaw();


    // ✅ GET BY ID
    @Query(value =
        "SELECT DISTINCT ON (sh.history_id) " +
        "sh.history_id, sh.service_id, sh.technician_id, " +
        "u.name AS technician_name, " +
        "a.name AS appliance_name, " +
        "b.bill_id, b.amount, " +
        "sh.reopen_date " +

        "FROM dev.service_history sh " +

        "JOIN dev.technician t " +
        "ON sh.technician_id = t.technician_id " +

        "JOIN dev.user_login ul " +
        "ON t.user_id = ul.user_id " +

        "JOIN dev.user u " +
        "ON ul.user_id = u.user_id " +

        "JOIN dev.service s " +
        "ON sh.service_id = s.service_id " +

        "JOIN dev.appliance a " +
        "ON s.appliance_id = a.appliance_id " +

        "LEFT JOIN dev.assignment ass " +
        "ON s.service_id = ass.service_id " +

        "LEFT JOIN dev.bill b " +
        "ON ass.assignment_id = b.assignment_id " +

        "WHERE sh.history_id = ?1 " +

        "ORDER BY sh.history_id, sh.reopen_date DESC",

        nativeQuery = true)
    List<Object[]> getHistoryByIdRaw(String id);


    // ✅ GET BY SERVICE ID
    @Query(value =
        "SELECT DISTINCT ON (sh.history_id) " +
        "sh.history_id, sh.service_id, sh.technician_id, " +
        "u.name AS technician_name, " +
        "a.name AS appliance_name, " +
        "b.bill_id, b.amount, " +
        "sh.reopen_date " +

        "FROM dev.service_history sh " +

        "JOIN dev.technician t " +
        "ON sh.technician_id = t.technician_id " +

        "JOIN dev.user_login ul " +
        "ON t.user_id = ul.user_id " +

        "JOIN dev.user u " +
        "ON ul.user_id = u.user_id " +

        "JOIN dev.service s " +
        "ON sh.service_id = s.service_id " +

        "JOIN dev.appliance a " +
        "ON s.appliance_id = a.appliance_id " +

        "LEFT JOIN dev.assignment ass " +
        "ON s.service_id = ass.service_id " +

        "LEFT JOIN dev.bill b " +
        "ON ass.assignment_id = b.assignment_id " +

        "WHERE sh.service_id = ?1 " +

        "ORDER BY sh.history_id, sh.reopen_date DESC",

        nativeQuery = true)
    List<Object[]> getHistoryByServiceIdRaw(String serviceId);
}
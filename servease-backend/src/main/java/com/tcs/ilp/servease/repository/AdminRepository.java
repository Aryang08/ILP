package com.tcs.ilp.servease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.ilp.servease.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    // Custom derived query
    boolean existsByUserId(String userId);
}
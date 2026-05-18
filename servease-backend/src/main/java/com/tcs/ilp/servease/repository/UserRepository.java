package com.tcs.ilp.servease.repository;

import com.tcs.ilp.servease.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // =========================
    // FIND USER BY EMAIL (LOGIN)
    // =========================
    Optional<User> findByEmail(String email);

    // =========================
    // FIND USER BY USER ID (LOGIN)
    // =========================
    Optional<User> findByUserId(String userId);

    // =========================
    // CHECK USER EXISTS
    // =========================
    boolean existsByUserId(String userId);

    // =========================
    // CHECK EMAIL EXISTS
    // =========================
    boolean existsByEmail(String email);
}

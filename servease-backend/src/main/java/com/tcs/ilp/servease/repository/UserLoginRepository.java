package com.tcs.ilp.servease.repository;

import com.tcs.ilp.servease.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, String> {

    //  fetch login details
    Optional<UserLogin> findByUserId(String userId);

    //  check if login exists
    boolean existsByUserId(String userId);
}
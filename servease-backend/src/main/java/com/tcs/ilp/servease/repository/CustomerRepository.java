package com.tcs.ilp.servease.repository;

import com.tcs.ilp.servease.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByCustomerId(String customerId);  // ✅ FIX

    Optional<Customer> findByUserId(String userId);

    List<Customer> findByPincode(String pincode);

    List<Customer> findByAddressContaining(String address);

    boolean existsByUserId(String userId);
}

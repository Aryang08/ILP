package com.tcs.ilp.servease.repository;

import com.tcs.ilp.servease.entity.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SparePartRepository extends JpaRepository<SparePart, Integer> {

    // Fetch spare parts by appliance category
    List<SparePart> findByCategory(String category);
}
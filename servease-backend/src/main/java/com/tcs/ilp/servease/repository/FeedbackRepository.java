package com.tcs.ilp.servease.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.ilp.servease.entity.FeedbackUser;

@Repository
public interface FeedbackRepository
        extends JpaRepository<FeedbackUser, String> {

    // ✅ Find feedbacks by serviceId with pagination
    Page<FeedbackUser> findByServiceIdOrderByCreatedAtDesc(
            String serviceId, Pageable pageable);

    // ✅ Find all feedbacks with pagination
    Page<FeedbackUser> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
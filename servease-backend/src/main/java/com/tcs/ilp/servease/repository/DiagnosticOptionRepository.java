package com.tcs.ilp.servease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.ilp.servease.entity.DiagnosticOption;

@Repository
public interface DiagnosticOptionRepository
        extends JpaRepository<DiagnosticOption, String> {

    List<DiagnosticOption> findByQuestionId(String questionId);
}
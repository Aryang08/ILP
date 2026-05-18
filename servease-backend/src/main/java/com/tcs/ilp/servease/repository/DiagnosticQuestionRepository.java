package com.tcs.ilp.servease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.ilp.servease.entity.DiagnosticQuestion;

@Repository
public interface DiagnosticQuestionRepository
        extends JpaRepository<DiagnosticQuestion, String> {

    List<DiagnosticQuestion> findByApplianceType(String applianceType);

    DiagnosticQuestion findByApplianceTypeAndIsRoot(String applianceType, boolean isRoot);
}

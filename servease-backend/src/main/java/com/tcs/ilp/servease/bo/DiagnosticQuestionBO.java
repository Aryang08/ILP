package com.tcs.ilp.servease.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import com.tcs.ilp.servease.entity.DiagnosticQuestion;
import com.tcs.ilp.servease.exception.DiagnosticException;
import com.tcs.ilp.servease.repository.DiagnosticQuestionRepository;

@Service
@Transactional
public class DiagnosticQuestionBO {

    @Autowired
    private DiagnosticQuestionRepository repo;

    public boolean addQuestion(DiagnosticQuestion question)
            throws DiagnosticException {

        if (question == null || question.getQuestionId() == null ||
            question.getQuestionId().isEmpty()) {
            throw new DiagnosticException("Invalid Question Data");
        }

        repo.save(question);
        return true;
    }

    public DiagnosticQuestion getQuestionById(String questionId)
            throws DiagnosticException {

        return repo.findById(questionId)
                .orElseThrow(() -> new DiagnosticException("Question not found"));
    }

    public List<DiagnosticQuestion> getQuestionsByAppliance(String applianceType) {
        return repo.findByApplianceType(applianceType);
    }

    public DiagnosticQuestion getRootQuestionByAppliance(String applianceType) {
        return repo.findByApplianceTypeAndIsRoot(applianceType, true);
    }

    public boolean updateQuestion(DiagnosticQuestion question) {
        repo.save(question);
        return true;
    }

    public boolean deleteQuestion(String questionId) {
        repo.deleteById(questionId);
        return true;
    }
}
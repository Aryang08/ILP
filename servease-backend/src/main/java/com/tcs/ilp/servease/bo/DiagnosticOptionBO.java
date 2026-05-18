package com.tcs.ilp.servease.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import com.tcs.ilp.servease.entity.DiagnosticOption;
import com.tcs.ilp.servease.exception.DiagnosticException;
import com.tcs.ilp.servease.repository.DiagnosticOptionRepository;

@Service
@Transactional
public class DiagnosticOptionBO {

    @Autowired
    private DiagnosticOptionRepository repo;

    public boolean addOption(DiagnosticOption option) {
        repo.save(option);
        return true;
    }

    public List<DiagnosticOption> getOptionsByQuestionId(String questionId) {
        return repo.findByQuestionId(questionId);
    }

    public DiagnosticOption getOptionById(String optionId)
            throws DiagnosticException {

        return repo.findById(optionId)
                .orElseThrow(() -> new DiagnosticException("Option not found"));
    }

    public boolean updateOption(DiagnosticOption option) {
        repo.save(option);
        return true;
    }

    public boolean deleteOption(String optionId) {
        repo.deleteById(optionId);
        return true;
    }
}
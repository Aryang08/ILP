package com.tcs.ilp.servease.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.ilp.servease.entity.DiagnosticOption;
import com.tcs.ilp.servease.entity.DiagnosticQuestion;
import com.tcs.ilp.servease.exception.DiagnosticException;

@Service
public class DiagnosticFlowBO {

    @Autowired
    private DiagnosticQuestionBO questionBO;

    @Autowired
    private DiagnosticOptionBO optionBO;

    /*
     * Step 1: Start diagnostic
     */
    public DiagnosticQuestion startDiagnostic(String applianceType)
            throws DiagnosticException {

        return questionBO.getRootQuestionByAppliance(applianceType);
    }

    /*
     * Step 2: Fetch options
     */
    public List<DiagnosticOption> getOptionsForQuestion(String questionId)
            throws DiagnosticException {

        return optionBO.getOptionsByQuestionId(questionId);
    }

    /*
     * Step 3: Go to next question
     */
    public DiagnosticQuestion moveToNextQuestion(String optionId)
            throws DiagnosticException {

        DiagnosticOption selectedOption = optionBO.getOptionById(optionId);

        if (selectedOption == null) {
            throw new DiagnosticException("Selected option not found");
        }

        String nextQuestionId = selectedOption.getNextQuestionId();

        if (nextQuestionId == null || nextQuestionId.trim().isEmpty()) {
            return null;
        }

        return questionBO.getQuestionById(nextQuestionId);
    }

    /*
     * Step 4: Check completion
     */
    public boolean isDiagnosticComplete(DiagnosticQuestion question) {
        return question != null && question.isTerminal();
    }
}
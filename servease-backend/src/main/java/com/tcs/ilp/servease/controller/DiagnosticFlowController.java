package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.DiagnosticFlowBO;
import com.tcs.ilp.servease.entity.DiagnosticOption;
import com.tcs.ilp.servease.entity.DiagnosticQuestion;
import com.tcs.ilp.servease.exception.DiagnosticException;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/diagnostic-flow")
public class DiagnosticFlowController {

    @Autowired
    private DiagnosticFlowBO flowBO;

    /**
     * STEP 1: Start Diagnostic
     */
    @GetMapping("/start/{applianceType}")
    public ResponseEntity<DiagnosticQuestion> startDiagnostic(
            @PathVariable String applianceType,
            HttpServletRequest request)
            throws DiagnosticException {

        AuthUtil.getSession(request); // ✅ validate session only

        DiagnosticQuestion question =
                flowBO.startDiagnostic(applianceType);

        return ResponseEntity.ok(question);
    }

    /**
     * STEP 2: Get Options
     */
    @GetMapping("/options/{questionId}")
    public ResponseEntity<List<DiagnosticOption>> getOptions(
            @PathVariable String questionId,
            HttpServletRequest request)
            throws DiagnosticException {

        AuthUtil.getSession(request);

        List<DiagnosticOption> options =
                flowBO.getOptionsForQuestion(questionId);

        return ResponseEntity.ok(options);
    }

    /**
     * STEP 3: Next Question
     */
    @GetMapping("/next/{optionId}")
    public ResponseEntity<DiagnosticQuestion> moveToNextQuestion(
            @PathVariable String optionId,
            HttpServletRequest request)
            throws DiagnosticException {

        AuthUtil.getSession(request);

        DiagnosticQuestion nextQuestion =
                flowBO.moveToNextQuestion(optionId);

        return ResponseEntity.ok(nextQuestion);
    }
}
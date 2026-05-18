package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.DiagnosticQuestionBO;
import com.tcs.ilp.servease.entity.DiagnosticQuestion;
import com.tcs.ilp.servease.exception.DiagnosticException;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/diagnostic-questions")
public class DiagnosticQuestionController {

    @Autowired
    private DiagnosticQuestionBO bo;

    // ✅ CREATE (ADMIN ONLY)
    @PostMapping
    public ResponseEntity<Boolean> addQuestion(
            @RequestBody DiagnosticQuestion question,
            HttpServletRequest request)
            throws DiagnosticException {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return ResponseEntity.ok(bo.addQuestion(question));
    }

    // ✅ GET BY ID (ALL LOGGED-IN USERS)
    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticQuestion> getQuestionById(
            @PathVariable("id") String questionId,
            HttpServletRequest request)
            throws DiagnosticException {

        AuthUtil.getSession(request); // only validation

        return ResponseEntity.ok(bo.getQuestionById(questionId));
    }

    // ✅ GET BY APPLIANCE (ALL LOGGED-IN USERS)
    @GetMapping("/appliance/{applianceType}")
    public ResponseEntity<List<DiagnosticQuestion>> getQuestionsByAppliance(
            @PathVariable String applianceType,
            HttpServletRequest request)
            throws DiagnosticException {

        AuthUtil.getSession(request);

        return ResponseEntity.ok(bo.getQuestionsByAppliance(applianceType));
    }

    // ✅ GET ROOT QUESTION (ALL LOGGED-IN USERS)
    @GetMapping("/appliance/{applianceType}/root")
    public ResponseEntity<DiagnosticQuestion> getRootQuestion(
            @PathVariable String applianceType,
            HttpServletRequest request)
            throws DiagnosticException {

        AuthUtil.getSession(request);

        return ResponseEntity.ok(
                bo.getRootQuestionByAppliance(applianceType)
        );
    }

    // ✅ UPDATE (ADMIN ONLY)
    @PutMapping
    public ResponseEntity<Boolean> updateQuestion(
            @RequestBody DiagnosticQuestion question,
            HttpServletRequest request)
            throws DiagnosticException {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return ResponseEntity.ok(bo.updateQuestion(question));
    }

    // ✅ DELETE (ADMIN ONLY)
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteQuestion(
            @PathVariable("id") String questionId,
            HttpServletRequest request)
            throws DiagnosticException {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return ResponseEntity.ok(bo.deleteQuestion(questionId));
    }
}

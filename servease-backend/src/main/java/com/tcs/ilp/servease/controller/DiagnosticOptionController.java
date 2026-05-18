package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.DiagnosticOptionBO;
import com.tcs.ilp.servease.entity.DiagnosticOption;
import com.tcs.ilp.servease.exception.DiagnosticException;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/diagnostic-options")
public class DiagnosticOptionController {

    @Autowired
    private DiagnosticOptionBO bo;

    // ✅ CREATE (ADMIN ONLY)
    @PostMapping
    public ResponseEntity<Boolean> addOption(
            @RequestBody DiagnosticOption option,
            HttpServletRequest request)
            throws DiagnosticException {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return ResponseEntity.ok(bo.addOption(option));
    }

    // ✅ GET BY ID (ADMIN / SUPERVISOR)
    @GetMapping("/{optionId}")
    public ResponseEntity<DiagnosticOption> getOptionById(
            @PathVariable String optionId,
            HttpServletRequest request)
            throws DiagnosticException {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return ResponseEntity.ok(bo.getOptionById(optionId));
    }

    // ✅ GET BY QUESTION (ADMIN / SUPERVISOR)
    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<DiagnosticOption>> getOptionsByQuestionId(
            @PathVariable String questionId,
            HttpServletRequest request)
            throws DiagnosticException {

        AuthUtil.getSession(request);

        return ResponseEntity.ok(bo.getOptionsByQuestionId(questionId));
    }

    // ✅ UPDATE (ADMIN ONLY)
    @PutMapping
    public ResponseEntity<Boolean> updateOption(
            @RequestBody DiagnosticOption option,
            HttpServletRequest request)
            throws DiagnosticException {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return ResponseEntity.ok(bo.updateOption(option));
    }

    // ✅ DELETE (ADMIN ONLY)
    @DeleteMapping("/{optionId}")
    public ResponseEntity<Boolean> deleteOption(
            @PathVariable String optionId,
            HttpServletRequest request)
            throws DiagnosticException {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return ResponseEntity.ok(bo.deleteOption(optionId));
    }
}
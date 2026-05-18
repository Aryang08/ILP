package com.tcs.ilp.servease.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.entity.ServiceDiagnostics;
import com.tcs.ilp.servease.bo.ServiceDiagnosticsBO;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.SessionManager;
import com.tcs.ilp.servease.config.Role;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/service-diagnostics")
public class ServiceDiagnosticsController {

    private final ServiceDiagnosticsBO diagnosticsService;

    @Autowired
    private SessionManager sessionManager;   // ✅ NEW (mockable)

    public ServiceDiagnosticsController(ServiceDiagnosticsBO diagnosticsService) {
        this.diagnosticsService = diagnosticsService;
    }

    // ✅ CENTRAL SESSION HANDLER (replaces AuthUtil)
    private SessionData getSession(HttpServletRequest request) {
        String sessionId = request.getHeader("sessionId");

        if (sessionId == null) {
            throw new RuntimeException("Missing sessionId");
        }

        SessionData session = sessionManager.getSession(sessionId);

        if (session == null) {
            throw new RuntimeException("Invalid session");
        }

        return session;
    }

    // ✅ ADD
    @PostMapping
    public ResponseEntity<String> addServiceDiagnostic(
            @RequestBody ServiceDiagnostics diagnostics,
            HttpServletRequest request) {

        SessionData session = getSession(request);

        if (session.getRole() != Role.CUSTOMER &&
            session.getRole() != Role.TECHNICIAN) {
            throw new RuntimeException("Access Denied");
        }

        diagnosticsService.addServiceDiagnostics(diagnostics);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Service diagnostic added successfully");
    }

    // ✅ GET BY ID
    @GetMapping("/{diagnosticId}")
    public ResponseEntity<ServiceDiagnostics> getByDiagnosticId(
            @PathVariable String diagnosticId,
            HttpServletRequest request) {

        getSession(request);

        ServiceDiagnostics diagnostics =
                diagnosticsService.getServiceDiagnosticsById(diagnosticId);

        return diagnostics == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(diagnostics);
    }

    // ✅ GET BY SERVICE
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<ServiceDiagnostics>> getByServiceId(
            @PathVariable String serviceId,
            HttpServletRequest request) {

        SessionData session = getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return ResponseEntity.ok(
                diagnosticsService.getServiceDiagnosticsByServiceId(serviceId)
        );
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<List<ServiceDiagnostics>> getAllDiagnostics(
            HttpServletRequest request) {

        SessionData session = getSession(request);

        if (session.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access Denied");
        }

        return ResponseEntity.ok(
                diagnosticsService.getAllServiceDiagnostics()
        );
    }

    // ✅ DELETE
    @DeleteMapping("/{diagnosticId}")
    public ResponseEntity<String> deleteDiagnostic(
            @PathVariable String diagnosticId,
            HttpServletRequest request) {

        SessionData session = getSession(request);

        if (session.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access Denied");
        }

        diagnosticsService.deleteServiceDiagnostics(diagnosticId);

        return ResponseEntity.ok("Service diagnostic deleted successfully");
    }

    // ✅ TIMESTAMP
    @GetMapping("/timestamp")
    public ResponseEntity<LocalDateTime> getAnswerTimestamp(
            @RequestParam String serviceId,
            @RequestParam String questionId,
            HttpServletRequest request) {

        getSession(request);

        LocalDateTime timestamp =
                diagnosticsService.getAnswerTimestamp(serviceId, questionId);

        return timestamp == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(timestamp);
    }

    // ✅ DELETE AFTER
    @DeleteMapping("/after")
    public ResponseEntity<String> deleteAnswersAfter(
            @RequestParam String serviceId,
            HttpServletRequest request) {

        SessionData session = getSession(request);

        if (session.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access Denied");
        }

        diagnosticsService.deleteAnswersAfter(serviceId);

        return ResponseEntity.ok("Answers deleted successfully");
    }

    // ✅ POSITIVE ANSWERS
    @GetMapping("/positive/{serviceId}")
    public ResponseEntity<List<ServiceDiagnostics>> getPositiveAnswers(
            @PathVariable String serviceId,
            HttpServletRequest request) {

        SessionData session = getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return ResponseEntity.ok(
                diagnosticsService.findPositiveAnswers(serviceId)
        );
    }
}
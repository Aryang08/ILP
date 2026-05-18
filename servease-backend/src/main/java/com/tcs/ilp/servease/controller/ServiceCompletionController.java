package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.ServiceCompletionBO;
import com.tcs.ilp.servease.entity.ServiceCompletion;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/service-completions")
public class ServiceCompletionController {

    private final ServiceCompletionBO bo;

    public ServiceCompletionController(ServiceCompletionBO bo) {
        this.bo = bo;
    }

    // ✅ CREATE (TECHNICIAN / ADMIN)
    @PostMapping
    public ResponseEntity<String> create(
            @RequestBody ServiceCompletion sc,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.TECHNICIAN &&
            session.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access Denied");
        }

        bo.addServiceCompletion(sc);
        return ResponseEntity.ok("Created");
    }

    // ✅ GET BY ID (ANY LOGGED-IN USER)
    @GetMapping("/{id}")
    public ResponseEntity<ServiceCompletion> get(
            @PathVariable String id,
            HttpServletRequest request)
            throws Exception {

        AuthUtil.getSession(request); // validate only

        return ResponseEntity.ok(bo.getCompletionById(id));
    }

    // ✅ GET BY SERVICE (ADMIN / SUPERVISOR)
    @GetMapping("/service/{serviceId}")
    public List<ServiceCompletion> getByService(
            @PathVariable String serviceId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return bo.getCompletionsByServiceId(serviceId);
    }

    // ✅ DELETE (ADMIN ONLY)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable String id,
            HttpServletRequest request)
            throws Exception {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        bo.deleteServiceCompletion(id);
        return ResponseEntity.ok("Deleted");
    }
}
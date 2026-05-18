package com.tcs.ilp.servease.controller;

import com.tcs.ilp.servease.bo.ServiceHistoryBOImpl;
import com.tcs.ilp.servease.dto.*;
import com.tcs.ilp.servease.entity.ServiceHistory;
import com.tcs.ilp.servease.exception.ServiceHistoryException;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/history")
public class ServiceHistoryController {

    @Autowired
    private ServiceHistoryBOImpl bo;

    // ✅ CREATE (TECHNICIAN / SUPERVISOR / ADMIN)
    @PostMapping
    public ResponseEntity<String> add(
            @RequestBody ServiceHistoryRequest req,
            HttpServletRequest request)
            throws ServiceHistoryException {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.TECHNICIAN &&
            session.getRole() != Role.SUPERVISOR &&
            session.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access Denied");
        }

        ServiceHistory history = new ServiceHistory();
        history.setHistoryId(req.getHistoryId());
        history.setServiceId(req.getServiceId());
        history.setTechnicianId(req.getTechnicianId());
        history.setReopenDate(req.getReopenDate());

        bo.addServiceHistory(history);

        return ResponseEntity.ok("Inserted ");
    }

    // ✅ GET BY ID (ANY LOGGED-IN USER)
    @GetMapping("/{id}")
    public ServiceHistoryDTO getById(
            @PathVariable String id,
            HttpServletRequest request)
            throws ServiceHistoryException {

        AuthUtil.getSession(request);

        return bo.getHistoryById(id);
    }

    // ✅ GET BY SERVICE ID (ADMIN / SUPERVISOR)
    @GetMapping("/service/{serviceId}")
    public List<ServiceHistoryDTO> getByServiceId(
            @PathVariable String serviceId,
            HttpServletRequest request)
            throws ServiceHistoryException {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return bo.getHistoryByServiceId(serviceId);
    }

    // ✅ GET ALL (ADMIN ONLY)
    @GetMapping
    public List<ServiceHistoryDTO> getAll(
            HttpServletRequest request)
            throws ServiceHistoryException {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return bo.getAllHistory();
    }

    // ✅ UPDATE (ADMIN ONLY)
    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable String id,
            @RequestBody ServiceHistoryRequest req,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        try {
            ServiceHistory history = new ServiceHistory();
            history.setServiceId(req.getServiceId());
            history.setTechnicianId(req.getTechnicianId());
            history.setReopenDate(req.getReopenDate());

            bo.updateServiceHistory(id, history);

            return ResponseEntity.ok("Updated ");

        } catch (ServiceHistoryException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
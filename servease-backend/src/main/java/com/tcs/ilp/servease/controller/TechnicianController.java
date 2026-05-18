package com.tcs.ilp.servease.controller;

import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.dto.TechnicianDTO;
import com.tcs.ilp.servease.bo.TechnicianBO;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/technicians")
public class TechnicianController {

    private final TechnicianBO service;

    public TechnicianController(TechnicianBO service) {
        this.service = service;
    }

    // ✅ GET TECHNICIAN (ANY LOGGED-IN USER)
    @GetMapping("/{id}")
    public TechnicianDTO get(@PathVariable String id,
                             HttpServletRequest request) {

        AuthUtil.getSession(request); // validate session only
        return service.getTechnician(id);
    }

    // ✅ DELETE TECHNICIAN (ADMIN / SUPERVISOR)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id,
                         HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        service.deleteTechnician(id);
        return "Deleted successfully";
    }
}
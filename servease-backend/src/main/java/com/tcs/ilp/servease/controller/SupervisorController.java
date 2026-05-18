package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import org.springframework.data.domain.Page;

import com.tcs.ilp.servease.bo.SupervisorBO;
import com.tcs.ilp.servease.dto.TechnicianOnboardingDTO;
import com.tcs.ilp.servease.entity.Supervisor;
import com.tcs.ilp.servease.entity.Technician;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/supervisor")
public class SupervisorController {

    @Autowired
    private SupervisorBO supervisorBO;

    // ✅ ONBOARD TECHNICIAN (SUPERVISOR ONLY)
    @PostMapping("/onboard/{supervisorId}")
    public String onboardTechnician(
            @Valid @RequestBody TechnicianOnboardingDTO dto,
            @PathVariable String supervisorId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        supervisorBO.onboardTechnician(dto, supervisorId);
        return "Technician onboarded successfully";
    }

    // ✅ GET SUPERVISOR BY ID (ANY LOGGED-IN USER)
    @GetMapping("/{supervisorId}")
    public Supervisor getSupervisor(
            @PathVariable String supervisorId,
            HttpServletRequest request) {

        AuthUtil.getSession(request);

        Supervisor supervisor = supervisorBO.getSupervisorById(supervisorId);

        if (supervisor == null) {
            throw new RuntimeException("Supervisor not found");
        }

        return supervisor;
    }
    
    // ✅ GET ALL SUPERVISORS (ADMIN ONLY)
    @GetMapping("/all")
    public List<Supervisor> getAllSupervisors(
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return supervisorBO.getAllSupervisors();
    }

    // ✅ PAGINATED TECHNICIANS (ADMIN / SUPERVISOR)
    @GetMapping("/technicians/page")
    public Page<Technician> getTechniciansPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return supervisorBO.getTechniciansPaginated(page, size);
    }

    // ✅ UPDATE SUPERVISOR (ADMIN ONLY)
    @PutMapping("/update")
    public String updateSupervisor(
            @RequestBody Supervisor supervisor,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        supervisorBO.updateSupervisor(supervisor);
        return "Supervisor updated successfully";
    }

    // ✅ DELETE SUPERVISOR (ADMIN ONLY)
    @DeleteMapping("/delete/{supervisorId}")
    public String deleteSupervisor(
            @PathVariable String supervisorId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        supervisorBO.deleteSupervisor(supervisorId);
        return "Supervisor deleted successfully";
    }

    // ✅ DELETE TECHNICIAN (SUPERVISOR / ADMIN)
    @DeleteMapping("/technician/{technicianId}")
    public String deleteTechnician(
            @PathVariable String technicianId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        supervisorBO.deleteTechnician(technicianId);
        return "Technician deleted successfully";
    }

    // ✅ VALIDATE (ANY LOGGED-IN USER)
    @GetMapping("/validate/{supervisorId}")
    public String validateSupervisor(
            @PathVariable String supervisorId,
            HttpServletRequest request) {

        AuthUtil.getSession(request);

        supervisorBO.viewServiceRequests(supervisorId);
        return "Supervisor validated successfully";
    }

    // ✅ ERROR HANDLER (UNCHANGED)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException ex) {
        return ex.getMessage();
    }
}

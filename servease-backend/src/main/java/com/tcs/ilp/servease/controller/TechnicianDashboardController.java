package com.tcs.ilp.servease.controller;

import com.tcs.ilp.servease.bo.TechnicianDashboardBO;
import com.tcs.ilp.servease.dto.TechnicianDashboardDTO;
import com.tcs.ilp.servease.entity.SparePart;
import com.tcs.ilp.servease.entity.StatusENUM;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/dashboard")
public class TechnicianDashboardController {

    private final TechnicianDashboardBO service;

    public TechnicianDashboardController(TechnicianDashboardBO service) {
        this.service = service;
    }

    // ✅ DASHBOARD (TECHNICIAN ONLY)
    @GetMapping("/{techId}")
    public List<TechnicianDashboardDTO> getDashboard(
            @PathVariable String techId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.TECHNICIAN) {
            throw new RuntimeException("Access Denied");
        }

        return service.getDashboard(techId);
    }

    // ✅ ASSIGNED (TECHNICIAN ONLY)
    @GetMapping("/{techId}/assigned")
    public List<Object[]> assigned(
            @PathVariable String techId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.TECHNICIAN) {
            throw new RuntimeException("Access Denied");
        }

        return service.getAssigned(techId);
    }

    // ✅ COMPLETED (TECHNICIAN ONLY)
    @GetMapping("/{techId}/completed")
    public List<Object[]> completed(
            @PathVariable String techId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.TECHNICIAN) {
            throw new RuntimeException("Access Denied");
        }

        return service.getCompleted(techId);
    }

    // ✅ HISTORY (TECHNICIAN ONLY)
    @GetMapping("/{techId}/history")
    public List<Object[]> history(
            @PathVariable String techId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.TECHNICIAN) {
            throw new RuntimeException("Access Denied");
        }

        return service.getHistory(techId);
    }

    // ✅ REQUESTS (TECHNICIAN ONLY)
    @GetMapping("/{techId}/requests")
    public List<Object[]> requests(
            @PathVariable String techId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.TECHNICIAN) {
            throw new RuntimeException("Access Denied");
        }

        return service.getRequests(techId);
    }

    // ✅ INITIATE JOB (TECHNICIAN ONLY)
    @PutMapping("/initiate")
    public String initiate(
            @RequestParam String assignmentId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.TECHNICIAN) {
            throw new RuntimeException("Access Denied");
        }

        service.updateStatus(assignmentId, StatusENUM.ON_THE_WAY);
        return "Technician is on the way";
    }

    // ✅ START JOB (TECHNICIAN ONLY)
    @PutMapping("/start")
    public String start(
            @RequestParam String assignmentId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.TECHNICIAN) {
            throw new RuntimeException("Access Denied");
        }

        service.updateStatus(assignmentId, StatusENUM.IN_PROGRESS);
        return "Job started";
    }

    // ✅ COMPLETE JOB (TECHNICIAN ONLY)
    @PutMapping("/complete")
    public String complete(
            @RequestParam String assignmentId,
            @RequestParam String serviceId,
            @RequestParam String description,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.TECHNICIAN) {
            throw new RuntimeException("Access Denied");
        }

        service.completeService(assignmentId, serviceId, description);
        return "Job completed and waiting for feedback";
    }

    // ✅ SPARE PARTS (TECHNICIAN ONLY)
    @GetMapping("/spareparts")
    public List<SparePart> getSpareParts(
            @RequestParam String category,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.TECHNICIAN) {
            throw new RuntimeException("Access Denied");
        }

        return service.getSparePartsByCategory(category);
    }

    // ✅ APPLIANCE HISTORY (TECHNICIAN / ADMIN)
    @GetMapping("/history/appliance")
    public List<Object[]> getHistoryByAppliance(
            @RequestParam String applianceId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.TECHNICIAN &&
            session.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access Denied");
        }

        return service.getHistoryByAppliance(applianceId);
    }
}
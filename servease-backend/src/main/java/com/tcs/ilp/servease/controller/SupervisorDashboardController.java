package com.tcs.ilp.servease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.SupervisorDashboardBO;
import com.tcs.ilp.servease.dto.SupervisorDashboardDTO;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.validation.constraints.NotBlank;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/dashboard")
public class SupervisorDashboardController {

    @Autowired
    private SupervisorDashboardBO dashboardService;

    // =========================
    // GET DASHBOARD (PAGINATED)
    // =========================
    @GetMapping("/{supervisorId}")
    public SupervisorDashboardDTO getDashboard(
            @PathVariable @NotBlank String supervisorId,

            @RequestParam(defaultValue = "0") int techPage,
            @RequestParam(defaultValue = "5") int techSize,

            @RequestParam(defaultValue = "0") int schedPage,
            @RequestParam(defaultValue = "5") int schedSize,

            HttpServletRequest request
    ) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.SUPERVISOR &&
            session.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access Denied");
        }

        return dashboardService.getDashboard(
                supervisorId,
                techPage,
                techSize,
                schedPage,
                schedSize
        );
    }
}
package com.tcs.ilp.servease.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.DashboardStatsBO;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/dashboard")
public class DashboardStatsController {

    @Autowired
    private DashboardStatsBO dashboardStatsBO;

    // ✅ Total Customers Card
    @GetMapping("/total-customers")
    public int getTotalCustomers(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return dashboardStatsBO.getTotalCustomers();
    }

    // ✅ Total Technicians Card
    @GetMapping("/total-technicians")
    public int getTotalTechnicians(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return dashboardStatsBO.getTotalTechnicians();
    }

    // ✅ Total Service Requests Card
    @GetMapping("/total-requests")
    public int getTotalServiceRequests(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return dashboardStatsBO.getTotalServiceRequests();
    }

    // ✅ Pending Services Card
    @GetMapping("/pending-services")
    public int getPendingServices(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return dashboardStatsBO.getPendingServices();
    }

    // ✅ In-Progress Services Card
    @GetMapping("/in-progress-services")
    public int getInProgressServices(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return dashboardStatsBO.getInProgressServices();
    }

    // ✅ Completed Services Card
    @GetMapping("/completed-services")
    public int getCompletedServices(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return dashboardStatsBO.getCompletedServices();
    }
}
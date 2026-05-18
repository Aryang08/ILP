package com.tcs.ilp.servease.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

import com.tcs.ilp.servease.bo.ApplianceBO;
import com.tcs.ilp.servease.dto.ApplianceDTO;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/appliance")
public class ApplianceController {

    @Autowired
    private ApplianceBO bo;

    // ✅ GET ALL (SUPERVISOR / ADMIN)
    @GetMapping("/all")
    public List<ApplianceDTO> getAll(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        // allow supervisor or admin
        if (session.getRole() != com.tcs.ilp.servease.config.Role.ADMIN &&
            session.getRole() != com.tcs.ilp.servease.config.Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return bo.getAllAppliances();
    }

    // ✅ GET BY ID (SUPERVISOR / ADMIN)
    @GetMapping("/{id}")
    public ResponseEntity<ApplianceDTO> getById(@PathVariable String id,
                                               HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        if (session.getRole() != com.tcs.ilp.servease.config.Role.ADMIN &&
            session.getRole() != com.tcs.ilp.servease.config.Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return ResponseEntity.ok(bo.getApplianceById(id));
    }

    // ✅ DELETE (ADMIN ONLY)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppliance(@PathVariable String id,
                                                  HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        bo.deleteAppliance(id);
        return ResponseEntity.ok("Appliance deleted successfully");
    }

    // ✅ STATS (ADMIN ONLY)

    @GetMapping("/stats/total")
    public int getTotal(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return bo.getTotalAppliances();
    }

    @GetMapping("/stats/in-warranty")
    public long getInWarranty(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return bo.getInWarrantyCount();
    }

    @GetMapping("/stats/expired")
    public long getExpired(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return bo.getExpiredWarrantyCount();
    }

    @GetMapping("/stats/by-model")
    public Map<String, Long> getByModel(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return bo.getAppliancesByModel();
    }

    @GetMapping("/stats/by-customer")
    public Map<String, Long> getByCustomer(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        return bo.getAppliancesPerCustomer();
    }
}

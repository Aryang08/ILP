package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

import com.tcs.ilp.servease.bo.ServiceMainBO;
import com.tcs.ilp.servease.entity.ServiceMain;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/service")
public class ServiceMainController {

    @Autowired
    private ServiceMainBO serviceMainBO;

    // ✅ ADD SERVICE (CUSTOMER)
    @PostMapping("/add")
    public String addService(@RequestBody ServiceMain s,
                             HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Access Denied");
        }

        serviceMainBO.addService(s);
        return "Service added successfully";
    }

    // ✅ GET SERVICE BY ID (ANY LOGGED-IN USER)
    @GetMapping("/get/{serviceId}")
    public ServiceMain getServiceById(@PathVariable String serviceId,
                                     HttpServletRequest request) {

        AuthUtil.getSession(request);
        return serviceMainBO.getServiceById(serviceId);
    }

    // ✅ UPDATE STATUS (SUPERVISOR)
    @PutMapping("/updateStatus/{serviceId}")
    public String updateServiceStatus(
            @PathVariable String serviceId,
            @RequestParam String status,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        serviceMainBO.updateServiceStatus(serviceId, status);
        return "Service status updated successfully";
    }

    // ✅ DELETE (ADMIN ONLY)
    @DeleteMapping("/delete/{serviceId}")
    public String deleteService(@PathVariable String serviceId,
                                HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        serviceMainBO.deleteService(serviceId);
        return "Service deleted successfully";
    }

    // ✅ GET ALL (ADMIN / SUPERVISOR)
    @GetMapping("/all")
    public List<ServiceMain> getAllServices(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return serviceMainBO.getAllServices();
    }

    // ✅ PAGINATION (ADMIN / SUPERVISOR)
    @GetMapping("/page")
    public Page<ServiceMain> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return serviceMainBO.getAllServicesPaginated(page, size);
    }
}

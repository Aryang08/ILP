package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.ServiceCenterBO;
import com.tcs.ilp.servease.dto.ServiceCenterDTO;
import com.tcs.ilp.servease.entity.ServiceCenter;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/servicecenter")
public class ServiceCenterController {

    @Autowired
    private ServiceCenterBO bo;

    // ✅ ADD (ADMIN ONLY)
    @PostMapping("/add")
    public String addServiceCenter(
            @RequestBody ServiceCenterDTO dto,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        bo.insertServiceCenter(dto);
        return "Service Center Added Successfully";
    }

    // ✅ FIND BY PINCODE (ANY LOGGED-IN USER)
    @GetMapping("/find/{pincode}")
    public String findByPincode(
            @PathVariable String pincode,
            HttpServletRequest request) {

        AuthUtil.getSession(request); // only validation

        return bo.findByPincode(pincode);
    }

    // ✅ GET BY ID (ANY LOGGED-IN USER)
    @GetMapping("/get/{scid}")
    public ServiceCenter getById(
            @PathVariable String scid,
            HttpServletRequest request) {

        AuthUtil.getSession(request);

        return bo.getById(scid);
    }

    // ✅ GET ALL (ADMIN / SUPERVISOR)
    @GetMapping("/getall")
    public List<ServiceCenter> getAllServiceCenters(
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return bo.getAllServiceCenters();
    }

    // ✅ UPDATE (ADMIN ONLY)
    @PutMapping("/update/{scid}")
    public String updateServiceCenter(
            @PathVariable String scid,
            @RequestBody ServiceCenterDTO dto,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        bo.updateServiceCenter(scid, dto);

        return "Service Center Updated Successfully";
    }

    // ✅ DELETE (ADMIN ONLY)
    @DeleteMapping("/delete/{scid}")
    public String deleteServiceCenter(
            @PathVariable String scid,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        bo.deleteServiceCenter(scid);

        return "Service Center Deleted Successfully";
    }
}

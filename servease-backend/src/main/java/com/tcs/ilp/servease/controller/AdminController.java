package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.AdminBO;
import com.tcs.ilp.servease.entity.Admin;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminBO adminBO;

    // ✅ 1. ADD ADMIN (ADMIN ONLY)
    @PostMapping("/add")
    public String addAdmin(@RequestBody Admin admin,
                           HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        try {
            adminBO.addAdmin(admin);
            return "Admin added successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // ✅ 2. GET ADMIN BY ID (ADMIN ONLY)
    @GetMapping("/get/{adminId}")
    public Object getAdminById(@PathVariable String adminId,
                              HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        try {
            return adminBO.getAdminById(adminId);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // ✅ 3. DELETE ADMIN (ADMIN ONLY)
    @DeleteMapping("/delete/{adminId}")
    public String deleteAdmin(@PathVariable String adminId,
                             HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        try {
            adminBO.deleteAdmin(adminId);
            return "Admin deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // ✅ 4. GET ALL ADMINS (ADMIN ONLY)
    @GetMapping("/all")
    public Object getAllAdmins(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        try {
            return adminBO.getAllAdmins();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}

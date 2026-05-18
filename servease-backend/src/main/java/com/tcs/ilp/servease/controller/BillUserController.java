package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.BillUserBO;
import com.tcs.ilp.servease.entity.BillUser;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/billusers")
public class BillUserController {

    @Autowired
    private BillUserBO billUserBO;

    // ✅ GET ALL (ADMIN / SUPERVISOR)
    @GetMapping
    public List<BillUser> getAllBillUsers(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return billUserBO.getAllBillUsers();
    }

    // ✅ GET BY ASSIGNMENT ID (ADMIN / SUPERVISOR)
    @GetMapping("/{assignmentId}")
    public BillUser getByAssignmentId(@PathVariable String assignmentId,
                                      HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return billUserBO.getBillUserByAssignmentId(assignmentId);
    }

    // ✅ ADD BILL USER (TECHNICIAN / ADMIN)
    @PostMapping
    public String addBillUser(@RequestBody BillUser billUser,
                              HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.TECHNICIAN) {
            throw new RuntimeException("Access Denied");
        }

        billUserBO.addBillUser(billUser);
        return "BillUser added successfully";
    }
}
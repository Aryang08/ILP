package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tcs.ilp.servease.bo.AssignmentTableBO;
import com.tcs.ilp.servease.dto.AssignmentTableDTO;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.util.AuthUtil;
import com.tcs.ilp.servease.config.Role;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/assignments")
public class AssignmentTableController {

    @Autowired
    private AssignmentTableBO assignmentTableBO;

    // ✅ GET ASSIGNMENTS WITH PAGINATION (TABLE VIEW)
    @GetMapping("/all")
    public List<AssignmentTableDTO> getAssignments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        // ✅ SESSION VALIDATION
        SessionData session = AuthUtil.getSession(request);

        // ✅ ROLE CHECK (ADMIN or SUPERVISOR)
        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return assignmentTableBO.getAssignments(page, size);
    }
}
package com.tcs.ilp.servease.controller;

import com.tcs.ilp.servease.bo.SpecializationBO;
import com.tcs.ilp.servease.entity.Specialization;
import com.tcs.ilp.servease.exception.Technicianexception;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/specializations")
public class SpecializationController {

    private final SpecializationBO bo;

    public SpecializationController(SpecializationBO bo) {
        this.bo = bo;
    }

    // ✅ GET (ADMIN / SUPERVISOR)
    @GetMapping("/{id}")
    public Specialization get(@PathVariable String id,
                              HttpServletRequest request)
            throws Technicianexception {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return bo.getSpecializationBySkillId(id);
    }

    // ✅ ADD (ADMIN ONLY)
    @PostMapping
    public String add(@RequestBody Specialization sp,
                      HttpServletRequest request)
            throws Technicianexception {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkAdmin(session);

        bo.addSpecialization(sp);
        return "Added Successfully";
    }
}
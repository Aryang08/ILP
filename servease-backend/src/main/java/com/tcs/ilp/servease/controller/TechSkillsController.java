package com.tcs.ilp.servease.controller;

import com.tcs.ilp.servease.bo.TechSkillsBO;
import com.tcs.ilp.servease.entity.TechSkills;
import com.tcs.ilp.servease.exception.Technicianexception;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.config.Role;
import com.tcs.ilp.servease.util.AuthUtil;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/techskills")
public class TechSkillsController {

    private final TechSkillsBO bo;

    public TechSkillsController(TechSkillsBO bo) {
        this.bo = bo;
    }

    // ✅ ADD (ADMIN / SUPERVISOR)
    @PostMapping
    public String add(@RequestBody TechSkills techSkill,
                      HttpServletRequest request)
            throws Technicianexception {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        bo.addTechSkill(techSkill);
        return "Tech skill added successfully";
    }

    // ✅ GET BY TECH ID (ANY LOGGED-IN USER)
    @GetMapping("/{techId}")
    public List<TechSkills> getByTechId(
            @PathVariable String techId,
            HttpServletRequest request)
            throws Technicianexception {

        AuthUtil.getSession(request); // validate only

        return bo.getSkillsByTechId(techId);
    }

    // ✅ GET ALL (ADMIN / SUPERVISOR)
    @GetMapping
    public List<TechSkills> getAll(
            HttpServletRequest request)
            throws Technicianexception {

        SessionData session = AuthUtil.getSession(request);

        if (session.getRole() != Role.ADMIN &&
            session.getRole() != Role.SUPERVISOR) {
            throw new RuntimeException("Access Denied");
        }

        return bo.getAllTechSkills();
    }
}

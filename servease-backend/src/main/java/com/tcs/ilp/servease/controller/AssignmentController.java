package com.tcs.ilp.servease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

import com.tcs.ilp.servease.bo.AssignmentBO;
import com.tcs.ilp.servease.dto.AssignmentDTO;
import com.tcs.ilp.servease.entity.Assignment;
import com.tcs.ilp.servease.config.SessionData;
import com.tcs.ilp.servease.util.AuthUtil;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentBO assignmentBO;

    // =========================
    // CREATE
    // =========================
    @PostMapping("/assign/{supervisorId}")
    public String assignTechnician(
            @Valid @RequestBody AssignmentDTO dto,
            @PathVariable String supervisorId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkSupervisor(session);

        return assignmentBO.assignTechnicianToService(dto, supervisorId);
    }

    // =========================
    // UPDATE
    // =========================
    @PutMapping("/complete/{assignmentId}")
    public String markCompleted(
            @PathVariable @NotBlank String assignmentId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkSupervisor(session);

        assignmentBO.markAssignmentAsCompleted(assignmentId);
        return "Assignment marked as COMPLETED";
    }

    @PutMapping("/delay/{assignmentId}")
    public String markDelayed(
            @PathVariable @NotBlank String assignmentId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkSupervisor(session);

        assignmentBO.markAssignmentAsDelayed(assignmentId);
        return "Assignment marked as DELAYED";
    }

    // =========================
    // UPDATE STATUS
    // =========================
    @PutMapping("/status/{assignmentId}")
    public String updateStatus(
            @PathVariable String assignmentId,
            @RequestParam @NotBlank String status,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkSupervisor(session);

        assignmentBO.updateAssignmentStatus(assignmentId, status);
        return "Status updated to: " + status;
    }

    // =========================
    // READ (BY ID)
    // =========================
    @GetMapping("/{assignmentId}")
    public Assignment getById(
            @PathVariable @NotBlank String assignmentId,
            HttpServletRequest request) {

        AuthUtil.getSession(request); // session validation only
        return assignmentBO.getAssignmentById(assignmentId);
    }

    // =========================
    // READ ALL
    // =========================
    @GetMapping("/all")
    public List<Assignment> getAll(HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkSupervisor(session);

        return assignmentBO.getAllAssignments();
    }

    // =========================
    // PAGINATION
    // =========================
    @GetMapping("/page")
    public Page<Assignment> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            HttpServletRequest request) {

        AuthUtil.getSession(request); // only validation
        return assignmentBO.getAllAssignmentsPaginated(page, size);
    }

    // =========================
    // DELETE
    // =========================
    @DeleteMapping("/delete/{assignmentId}")
    public String delete(
            @PathVariable @NotBlank String assignmentId,
            HttpServletRequest request) {

        SessionData session = AuthUtil.getSession(request);
        AuthUtil.checkSupervisor(session);

        assignmentBO.deleteAssignment(assignmentId);
        return "Assignment deleted successfully";
    }
}

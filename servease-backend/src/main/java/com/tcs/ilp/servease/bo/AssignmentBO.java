package com.tcs.ilp.servease.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tcs.ilp.servease.dto.AssignmentDTO;
import com.tcs.ilp.servease.entity.Assignment;
import com.tcs.ilp.servease.entity.ServiceMain;
import com.tcs.ilp.servease.entity.Technician;

import com.tcs.ilp.servease.exception.InvalidInputException;
import com.tcs.ilp.servease.exception.ResourceNotFoundException;

import com.tcs.ilp.servease.repository.*;

@Service
public class AssignmentBO {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ServiceMainRepository serviceMainRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private TechSkillsRepository techSkillsRepository;

    // =========================
    //  GENERATE CUSTOM ID
    // =========================
    private String generateAssignmentId() {

        String lastId = assignmentRepository.findLastAssignmentId();

        if (lastId == null) {
            return "as1";
        }

        int num = Integer.parseInt(lastId.substring(2));
        return "as" + (num + 1);
    }

    // =========================
    //  ASSIGN TECHNICIAN
    // =========================
    @Transactional
    public String assignTechnicianEntity(
            Assignment assignment,
            String supervisorId
    ) {

        if (assignment == null) {
            throw new InvalidInputException("Assignment details are missing");
        }

        if (supervisorId == null) {
            throw new InvalidInputException("Supervisor ID is required");
        }

        String supervisorSC = supervisorRepository
                .findById(supervisorId)
                .map(s -> s.getServiceCenterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supervisor not found"));

        //  FIXED: use ServiceMain correctly
        ServiceMain service = serviceMainRepository
                .findById(assignment.getServiceId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Service request not found"));

        Technician technician = technicianRepository
                .findById(assignment.getTechnicianId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Technician not found"));

        // =========================
        //  VALIDATIONS
        // =========================
        if (!supervisorSC.equals(service.getServiceCenterId())) {
            throw new InvalidInputException("Service belongs to different SC");
        }

        if (!supervisorSC.equals(technician.getServiceCenterId())) {
            throw new InvalidInputException("Technician belongs to different SC");
        }

        //  Skill check
        if (!techSkillsRepository.existsByIdTechIdAndIdSkillId(
                technician.getTechnicianId(),
                service.getApplianceId()
        )) {
            throw new InvalidInputException("Technician does not have required skill");
        }

        //  Duplicate check
        if (assignmentRepository.existsByServiceId(service.getServiceId())) {
            throw new InvalidInputException("Service already assigned");
        }

        //  Workload check
        long activeAssignments = assignmentRepository
                .countByTechnicianIdAndStatusNot(
                        technician.getTechnicianId(),
                        "COMPLETED"
                );

        if (activeAssignments >= 3) {
            throw new InvalidInputException("Technician is overloaded");
        }

        // =========================
        //  SAVE
        // =========================
        assignment.setServiceCenterId(supervisorSC);

        if (assignment.getStatus() == null) {
            assignment.setStatus("ASSIGNED");
        }

        assignment.setAssignmentId(generateAssignmentId());

        Assignment saved = assignmentRepository.save(assignment);

        return saved.getAssignmentId();
    }

    // =========================
    //  GET ALL
    // =========================
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    // =========================
    //  PAGINATED
    // =========================
    public Page<Assignment> getAllAssignmentsPaginated(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return assignmentRepository.findAll(pageable);
    }

    // =========================
    //  COMPLETE
    // =========================
    @Transactional
    public void markAssignmentAsCompleted(String assignmentId) {

        Assignment assignment = assignmentRepository
                .findById(assignmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Assignment not found"));

        assignment.setStatus("COMPLETED");
        assignment.setCompletedAt(java.time.LocalDateTime.now());

        assignmentRepository.save(assignment);
    }

    // =========================
    //  DELAYED
    // =========================
    @Transactional
    public void markAssignmentAsDelayed(String assignmentId) {

        Assignment assignment = assignmentRepository
                .findById(assignmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Assignment not found"));

        assignment.setStatus("DELAYED");
        assignmentRepository.save(assignment);
    }

    // =========================
    //  UPDATE STATUS
    // =========================
    @Transactional
    public void updateAssignmentStatus(String assignmentId, String status) {

        Assignment assignment = assignmentRepository
                .findById(assignmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Assignment not found"));

        assignment.setStatus(status);
        assignmentRepository.save(assignment);
    }

    // =========================
    //  GET BY ID
    // =========================
    public Assignment getAssignmentById(String assignmentId) {

        return assignmentRepository
                .findById(assignmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Assignment not found"));
    }

    // =========================
    //  DELETE
    // =========================
    @Transactional
    public void deleteAssignment(String assignmentId) {

        if (!assignmentRepository.existsById(assignmentId)) {
            throw new ResourceNotFoundException("Assignment not found");
        }

        assignmentRepository.deleteById(assignmentId);
    }

    // =========================
    //  DTO ADAPTER
    // =========================
    public String assignTechnicianToService(
            AssignmentDTO dto,
            String supervisorId
    ) {

        Assignment assignment = new Assignment(
                null,
                dto.getServiceId(),
                dto.getTechnicianId(),
                null,
                dto.getCompletedAt(),
                dto.getStatus()
        );

        return assignTechnicianEntity(assignment, supervisorId);
    }
}

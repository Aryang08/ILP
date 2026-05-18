package com.tcs.ilp.servease.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tcs.ilp.servease.dto.SupervisorDTO;
import com.tcs.ilp.servease.dto.TechnicianOnboardingDTO;

import com.tcs.ilp.servease.entity.Supervisor;
import com.tcs.ilp.servease.entity.Technician;
import com.tcs.ilp.servease.entity.User;
import com.tcs.ilp.servease.entity.UserLogin;
import com.tcs.ilp.servease.entity.TechSkills;

import com.tcs.ilp.servease.exception.InvalidInputException;
import com.tcs.ilp.servease.exception.ResourceNotFoundException;

import com.tcs.ilp.servease.repository.*;

import com.tcs.ilp.servease.util.PasswordUtil;

@Service
public class SupervisorBO {

    @Autowired
    private SupervisorRepository supervisorRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private TechSkillsRepository techSkillsRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    // =========================
    // BASIC ONBOARD
    // =========================
    @Transactional
    public void onboardTechnician(Technician technician, String supervisorId) {

        if (technician == null) {
            throw new InvalidInputException("Technician details are missing");
        }

        String scId = supervisorRepository
                .findById(supervisorId)
                .map(s -> s.getServiceCenterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supervisor not found"));

        if (technicianRepository.existsById(technician.getTechnicianId())) {
            throw new InvalidInputException("Technician already exists");
        }

        technician.setServiceCenterId(scId);

        technicianRepository.save(technician);
    }

    // =========================
    // FULL ONBOARD
    // =========================
    @Transactional
    public void onboardTechnician(TechnicianOnboardingDTO dto, String supervisorId) {

        String scId = supervisorRepository
                .findById(supervisorId)
                .map(s -> s.getServiceCenterId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supervisor not found"));

        String userId = "U_" + dto.getTechnicianId();

        if (technicianRepository.existsById(dto.getTechnicianId())) {
            throw new InvalidInputException("Technician already exists");
        }

        if (userRepository.existsByUserId(userId)) {
            throw new InvalidInputException("User already exists");
        }

        // USER
        User user = new User(
                userId,
                dto.getName(),
                dto.getPhone(),
                dto.getEmail(),
                "TECHNICIAN"
        );
        userRepository.save(user);

        // LOGIN
        String salt = PasswordUtil.generateSalt();
        String hashedPassword = PasswordUtil.hashPassword(dto.getPassword(), salt);

        userLoginRepository.save(new UserLogin(userId, hashedPassword, salt, true));

        // TECHNICIAN
        Technician technician = new Technician(
                dto.getTechnicianId(),
                userId,
                scId
        );
        technicianRepository.save(technician);

        // SKILLS
        if (dto.getSkillIds() != null) {
            for (String skillId : dto.getSkillIds()) {
                techSkillsRepository.save(
                        new TechSkills(dto.getTechnicianId(), skillId)
                );
            }
        }
    }

    // =========================
    // DELETE TECHNICIAN
    // =========================
    @Transactional
    public void deleteTechnician(String technicianId) {

        Technician tech = technicianRepository.findById(technicianId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Technician not found"));

        long activeAssignments = assignmentRepository.countActiveAssignments(technicianId);

        if (activeAssignments > 0) {
            throw new InvalidInputException("Technician has active assignments");
        }

        String userId = tech.getUserId();

        techSkillsRepository.deleteByIdTechId(technicianId);
        technicianRepository.deleteById(technicianId);
        userLoginRepository.deleteById(userId);
        userRepository.deleteById(userId);
    }

    // =========================
    //  PAGINATION (NEW)
    // =========================
    public Page<Technician> getTechniciansPaginated(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return technicianRepository.findAll(pageable);
    }

    // =========================
    // SUPERVISOR METHODS
    // =========================
    public Supervisor getSupervisorById(String supervisorId) {

        return supervisorRepository.findById(supervisorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supervisor not found"));
    }

    public List<Supervisor> getAllSupervisors() {
        return supervisorRepository.findAll();
    }

    @Transactional
    public void deleteSupervisor(String supervisorId) {

        Supervisor supervisor = supervisorRepository.findById(supervisorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supervisor not found"));

        if (technicianRepository.countByServiceCenterId(
                supervisor.getServiceCenterId()) > 0) {

            throw new InvalidInputException("Cannot delete supervisor, technicians exist");
        }

        supervisorRepository.deleteById(supervisorId);
    }

    @Transactional
    public void updateSupervisor(Supervisor supervisor) {

        if (!supervisorRepository.existsById(supervisor.getSupervisorId())) {
            throw new ResourceNotFoundException("Supervisor not found");
        }

        supervisorRepository.save(supervisor);
    }

    public void viewServiceRequests(String supervisorId) {

        if (!supervisorRepository.existsById(supervisorId)) {
            throw new ResourceNotFoundException("Supervisor not found");
        }
    }
}

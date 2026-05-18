package com.tcs.ilp.servease.bo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.tcs.ilp.servease.dto.TechnicianOnboardingDTO;
import com.tcs.ilp.servease.entity.*;
import com.tcs.ilp.servease.exception.InvalidInputException;
import com.tcs.ilp.servease.repository.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SupervisorBOTest {

    @InjectMocks
    private SupervisorBO supervisorBO;

    @Mock
    private SupervisorRepository supervisorRepository;

    @Mock
    private TechnicianRepository technicianRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLoginRepository userLoginRepository;

    @Mock
    private TechSkillsRepository techSkillsRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    // =========================
    //  ONBOARD SUCCESS
    // =========================
    @Test
    void onboardTechnician_success() {

        TechnicianOnboardingDTO dto = new TechnicianOnboardingDTO();
        dto.setTechnicianId("T1");
        dto.setName("Aryan");
        dto.setEmail("test@mail.com");
        dto.setPhone("9999999999");
        dto.setPassword("pass");
        dto.setSkillIds(List.of("AC"));

        Supervisor supervisor = new Supervisor();
        supervisor.setServiceCenterId("SC1");

        when(supervisorRepository.findById("SUP1"))
                .thenReturn(Optional.of(supervisor));

        when(technicianRepository.existsById("T1")).thenReturn(false);
        when(userRepository.existsByUserId("U_T1")).thenReturn(false);

        supervisorBO.onboardTechnician(dto, "SUP1");

        verify(userRepository).save(any(User.class));
        verify(userLoginRepository).save(any(UserLogin.class));
        verify(technicianRepository).save(any(Technician.class));
        verify(techSkillsRepository, times(1)).save(any(TechSkills.class));
    }

    // =========================
    //  ONBOARD DUPLICATE
    // =========================
    @Test
    void onboardTechnician_duplicate() {

        TechnicianOnboardingDTO dto = new TechnicianOnboardingDTO();
        dto.setTechnicianId("T1");

		Supervisor supervisor = new Supervisor();
		supervisor.setServiceCenterId("SC1");

        when(supervisorRepository.findById("SUP1"))
                .thenReturn(Optional.of(supervisor));

        when(technicianRepository.existsById("T1")).thenReturn(true);

        assertThrows(InvalidInputException.class, () ->
                supervisorBO.onboardTechnician(dto, "SUP1"));
    }

    // =========================
    //  DELETE TECHNICIAN SUCCESS
    // =========================
    @Test
    void deleteTechnician_success() {

        Technician technician = new Technician();
        technician.setTechnicianId("T1");
        technician.setUserId("U_T1"); //  important

        when(technicianRepository.findById("T1"))
                .thenReturn(Optional.of(technician));

        when(assignmentRepository.countActiveAssignments("T1"))
                .thenReturn(0L);

        supervisorBO.deleteTechnician("T1");

        verify(techSkillsRepository).deleteByIdTechId("T1");
        verify(technicianRepository).deleteById("T1");
        verify(userLoginRepository).deleteById("U_T1");
        verify(userRepository).deleteById("U_T1");
    }

    // =========================
    //  DELETE TECHNICIAN BLOCKED
    // =========================
    @Test
    void deleteTechnician_activeAssignments() {

        Technician technician = new Technician();
        technician.setTechnicianId("T1");

        when(technicianRepository.findById("T1"))
                .thenReturn(Optional.of(technician));

        when(assignmentRepository.countActiveAssignments("T1"))
                .thenReturn(2L);

        assertThrows(InvalidInputException.class, () ->
                supervisorBO.deleteTechnician("T1"));
    }

    // =========================
    //  DELETE SUPERVISOR SUCCESS
    // =========================
    @Test
    void deleteSupervisor_success() {

        Supervisor supervisor = new Supervisor();
        supervisor.setServiceCenterId("SC1");

        when(supervisorRepository.findById("SUP1"))
                .thenReturn(Optional.of(supervisor));

        when(technicianRepository.countByServiceCenterId("SC1"))
                .thenReturn(0L);

        supervisorBO.deleteSupervisor("SUP1");

        verify(supervisorRepository).deleteById("SUP1");
    }

    // =========================
    //  DELETE SUPERVISOR BLOCKED
    // =========================
    @Test
    void deleteSupervisor_blocked() {

        Supervisor supervisor = new Supervisor();
        supervisor.setServiceCenterId("SC1");

        when(supervisorRepository.findById("SUP1"))
                .thenReturn(Optional.of(supervisor));

        when(technicianRepository.countByServiceCenterId("SC1"))
                .thenReturn(3L);

        assertThrows(InvalidInputException.class, () ->
                supervisorBO.deleteSupervisor("SUP1"));
    }

    // =========================
    //  GET SUPERVISOR
    // =========================
    @Test
    void getSupervisor_success() {

        when(supervisorRepository.findById("SUP1"))
                .thenReturn(Optional.of(new Supervisor()));

        Supervisor result = supervisorBO.getSupervisorById("SUP1");

        assertNotNull(result);
    }

    // =========================
    //  UPDATE SUPERVISOR
    // =========================
    @Test
    void updateSupervisor_success() {

        Supervisor supervisor = new Supervisor();
        supervisor.setSupervisorId("SUP1");

        when(supervisorRepository.existsById("SUP1"))
                .thenReturn(true);

        supervisorBO.updateSupervisor(supervisor);

        verify(supervisorRepository).save(supervisor);
    }

    // =========================
    //  VIEW VALIDATION
    // =========================
    @Test
    void viewServiceRequests_success() {

        when(supervisorRepository.existsById("SUP1"))
                .thenReturn(true);

        supervisorBO.viewServiceRequests("SUP1");

        verify(supervisorRepository).existsById("SUP1");
    }
}
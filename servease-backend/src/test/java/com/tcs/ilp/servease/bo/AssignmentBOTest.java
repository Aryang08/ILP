package com.tcs.ilp.servease.bo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.tcs.ilp.servease.entity.Assignment;
import com.tcs.ilp.servease.entity.Technician;
import com.tcs.ilp.servease.entity.Supervisor;
import com.tcs.ilp.servease.entity.ServiceMain;

import com.tcs.ilp.servease.repository.*;
import com.tcs.ilp.servease.exception.InvalidInputException;
import com.tcs.ilp.servease.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AssignmentBOTest {

    @InjectMocks
    private AssignmentBO assignmentBO;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private ServiceMainRepository serviceMainRepository;

    @Mock
    private TechnicianRepository technicianRepository;

    @Mock
    private SupervisorRepository supervisorRepository;

    @Mock
    private TechSkillsRepository techSkillsRepository;

    // =========================
    //  SUCCESS CASE
    // =========================
    @Test
    void assignTechnician_success() {

        Assignment assignment = new Assignment();
        assignment.setServiceId("S1");
        assignment.setTechnicianId("T1");

        Supervisor supervisor = new Supervisor();
        supervisor.setServiceCenterId("SC1");

        ServiceMain ServiceMain = new ServiceMain();
        ServiceMain.setServiceCenterId("SC1");
        ServiceMain.setApplianceId("SK1");

        Technician technician = new Technician();
        technician.setTechnicianId("T1");   // 
        technician.setServiceCenterId("SC1");

        when(supervisorRepository.findById("SUP1")).thenReturn(Optional.of(supervisor));
        when(serviceMainRepository.findById("S1")).thenReturn(Optional.of(ServiceMain));
        when(technicianRepository.findById("T1")).thenReturn(Optional.of(technician));
        when(assignmentRepository.findLastAssignmentId()).thenReturn("as1");

        //  REQUIRED MOCKS
        when(techSkillsRepository.existsByIdTechIdAndIdSkillId("T1", "SK1"))
                .thenReturn(true);
        when(assignmentRepository.existsByServiceId("S1")).thenReturn(false);
        when(assignmentRepository.countByTechnicianIdAndStatusNot("T1", "COMPLETED"))
                .thenReturn(0L);

        when(assignmentRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        String result = assignmentBO.assignTechnicianEntity(assignment, "SUP1");

        assertNotNull(result);
        assertTrue(result.startsWith("as"));
    }

    // =========================
    //  NULL ASSIGNMENT
    // =========================
    @Test
    void assignTechnician_nullAssignment() {

        assertThrows(InvalidInputException.class, () ->
                assignmentBO.assignTechnicianEntity(null, "SUP1"));
    }

    // =========================
    //  INVALID SERVICE CENTER
    // =========================
    @Test
    void assignTechnician_invalidServiceCenter() {

        Assignment assignment = new Assignment();
        assignment.setServiceId("S1");
        assignment.setTechnicianId("T1");

        Supervisor supervisor = new Supervisor();
        supervisor.setServiceCenterId("SC1");

        ServiceMain ServiceMain = new ServiceMain();
        ServiceMain.setServiceCenterId("SC2");

        Technician technician = new Technician();
        technician.setServiceCenterId("SC1");

        when(supervisorRepository.findById("SUP1")).thenReturn(Optional.of(supervisor));
        when(serviceMainRepository.findById("S1")).thenReturn(Optional.of(ServiceMain));
        when(technicianRepository.findById("T1")).thenReturn(Optional.of(technician));

        assertThrows(InvalidInputException.class, () ->
                assignmentBO.assignTechnicianEntity(assignment, "SUP1"));
    }

    // =========================
    //  MARK COMPLETED
    // =========================
    @Test
    void markCompleted_success() {

        Assignment assignment = new Assignment();

        when(assignmentRepository.findById("as1"))
                .thenReturn(Optional.of(assignment));

        assignmentBO.markAssignmentAsCompleted("as1");

        assertEquals("COMPLETED", assignment.getStatus());
        assertNotNull(assignment.getCompletedAt());
    }

    // =========================
    //  DELETE SUCCESS
    // =========================
    @Test
    void deleteAssignment_success() {

        when(assignmentRepository.existsById("as1")).thenReturn(true);

        assignmentBO.deleteAssignment("as1");

        verify(assignmentRepository).deleteById("as1");
    }

    // =========================
    //  DELETE NOT FOUND
    // =========================
    @Test
    void deleteAssignment_notFound() {

        when(assignmentRepository.existsById("as1")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () ->
                assignmentBO.deleteAssignment("as1"));
    }
}
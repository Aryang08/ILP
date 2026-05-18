package com.tcs.ilp.servease.bo;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.ilp.servease.dto.AssignmentSummaryDTO;
import com.tcs.ilp.servease.dto.TechnicianAssignmentDTO;

import com.tcs.ilp.servease.repository.AssignmentRepository;

@Service
public class TechnicianAssignmentBO {

    @Autowired
    private AssignmentRepository assignmentRepository;

    // =========================
    // GET SUMMARY 
    // =========================
    public AssignmentSummaryDTO getAssignmentSummary() {

        AssignmentSummaryDTO dto = new AssignmentSummaryDTO();

        dto.setUnassigned(assignmentRepository.countUnassigned());
        dto.setAssigned(assignmentRepository.countByStatus("ASSIGNED"));
        dto.setDelayed(assignmentRepository.countDelayed());

        return dto;
    }

    // =========================
    // GET ASSIGNMENT LIST 
    // =========================
    public List<TechnicianAssignmentDTO> getAssignments(String supervisorId) {

        //  Raw query result from repository (Object[])
        List<Object[]> results =
                assignmentRepository.getAssignmentTableRaw(supervisorId);

        List<TechnicianAssignmentDTO> list = new ArrayList<>();

        for (Object[] row : results) {

            TechnicianAssignmentDTO dto = new TechnicianAssignmentDTO();

            dto.setAssignmentId((String) row[0]);
            dto.setServiceId((String) row[1]);
            dto.setTechnicianName((String) row[2]);
            dto.setStatus((String) row[3]);

            list.add(dto);
        }

        return list;
    }
}
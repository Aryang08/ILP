
package com.tcs.ilp.servease.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.ilp.servease.dto.AssignmentTableDTO;
import com.tcs.ilp.servease.repository.AssignmentTableRepository;

@Service
public class AssignmentTableBO {

    @Autowired
    private AssignmentTableRepository assignmentTableRepository;

    public List<AssignmentTableDTO> getAssignments(int page, int size) {
        int offset = page * size;
        return assignmentTableRepository.getAssignments(size, offset);
    }
}

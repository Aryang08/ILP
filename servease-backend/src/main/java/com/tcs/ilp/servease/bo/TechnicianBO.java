package com.tcs.ilp.servease.bo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.ilp.servease.dto.TechnicianDTO;
import com.tcs.ilp.servease.entity.Technician;
import com.tcs.ilp.servease.exception.Technicianexception;
import com.tcs.ilp.servease.exception.Technicianexception.ErrorCode;
import com.tcs.ilp.servease.repository.TechnicianRepository;

@Service
public class TechnicianBO {

    private final TechnicianRepository repo;

    public TechnicianBO(TechnicianRepository repo) {
        this.repo = repo;
    }

    // ✅ ADD TECHNICIAN
    @Transactional
    public TechnicianDTO addTechnician(Technician technician) {

        Technician saved = repo.save(technician);

        // ✅ Convert Entity → DTO
        return new TechnicianDTO(
                saved.getTechnicianId(),
                saved.getUserId(),
                saved.getServiceCenterId()
        );
    }

    // ✅ GET TECHNICIAN BY ID
    @Transactional(readOnly = true)
    public TechnicianDTO getTechnician(String id) {

        Technician tech = repo.findById(id)
                .orElseThrow(() ->
                        new Technicianexception(
                                ErrorCode.TECHNICIAN_NOT_FOUND,
                                "Technician not found: " + id
                        ));

        // ✅ Convert Entity → DTO
        return new TechnicianDTO(
                tech.getTechnicianId(),
                tech.getUserId(),
                tech.getServiceCenterId()
        );
    }

    // ✅ DELETE TECHNICIAN
    @Transactional
    public void deleteTechnician(String id) {

        Technician tech = repo.findById(id)
                .orElseThrow(() ->
                        new Technicianexception(
                                ErrorCode.TECHNICIAN_NOT_FOUND,
                                "Technician not found: " + id
                        ));

        repo.delete(tech);
    }
}

package com.tcs.ilp.servease.bo;

import com.tcs.ilp.servease.entity.Specialization;
import com.tcs.ilp.servease.exception.Technicianexception;
import com.tcs.ilp.servease.exception.Technicianexception.ErrorCode;
import com.tcs.ilp.servease.repository.SpecializationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecializationBO {

    private final SpecializationRepository repo;

    public SpecializationBO(SpecializationRepository repo) {
        this.repo = repo;
    }

    public void addSpecialization(Specialization sp) throws Technicianexception {

        if (sp == null)
            throw new Technicianexception(ErrorCode.INVALID_INPUT, "Specialization cannot be null");

        if (sp.getSkillId() == null || sp.getSkillId().isBlank())
            throw new Technicianexception(ErrorCode.INVALID_INPUT, "Skill ID cannot be empty");

        if (sp.getSpecialization() == null || sp.getSpecialization().isBlank())
            throw new Technicianexception(ErrorCode.INVALID_INPUT, "Specialization cannot be empty");

        repo.save(sp);
    }

    public Specialization getSpecializationBySkillId(String skillId) throws Technicianexception {

        return repo.findById(skillId)
                .orElseThrow(() ->
                        new Technicianexception(ErrorCode.NOT_FOUND, "Specialization not found: " + skillId));
    }

    public List<Specialization> getAllSpecializations() {
        return repo.findAll();
    }

    public boolean updateSpecialization(Specialization sp) throws Technicianexception {

        if (!repo.existsById(sp.getSkillId()))
            throw new Technicianexception(ErrorCode.NOT_FOUND, "Specialization not found: " + sp.getSkillId());

        repo.save(sp);
        return true;
    }

    public boolean deleteSpecialization(String skillId) throws Technicianexception {

        if (!repo.existsById(skillId))
            throw new Technicianexception(ErrorCode.NOT_FOUND, "Specialization not found: " + skillId);

        repo.deleteById(skillId);
        return true;
    }
}
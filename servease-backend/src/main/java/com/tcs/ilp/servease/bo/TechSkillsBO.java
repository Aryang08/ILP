package com.tcs.ilp.servease.bo;

import org.springframework.stereotype.Service;

import com.tcs.ilp.servease.repository.TechSkillsRepository;
import com.tcs.ilp.servease.entity.TechSkills;
import com.tcs.ilp.servease.exception.Technicianexception;
import com.tcs.ilp.servease.exception.Technicianexception.ErrorCode;

import java.util.List;

@Service
public class TechSkillsBO {

    private final TechSkillsRepository repo;

    public TechSkillsBO(TechSkillsRepository repo) {
        this.repo = repo;
    }

    public void addTechSkill(TechSkills techSkill) throws Technicianexception {

        if (techSkill == null)
            throw new Technicianexception(ErrorCode.INVALID_INPUT, "TechSkills cannot be null");

        if (techSkill.getTechId() == null || techSkill.getTechId().isBlank())
            throw new Technicianexception(ErrorCode.INVALID_INPUT, "Technician ID cannot be empty");

        if (techSkill.getSkillId() == null || techSkill.getSkillId().isBlank())
            throw new Technicianexception(ErrorCode.INVALID_INPUT, "Skill ID cannot be empty");

        repo.save(techSkill);
    }

    public List<TechSkills> getSkillsByTechId(String techId) throws Technicianexception {

        if (techId == null || techId.isBlank())
            throw new Technicianexception(ErrorCode.INVALID_INPUT, "Technician ID cannot be empty");

        return repo.findByIdTechId(techId);
    }

    public List<TechSkills> getAllTechSkills() {
        return repo.findAll();
    }
}
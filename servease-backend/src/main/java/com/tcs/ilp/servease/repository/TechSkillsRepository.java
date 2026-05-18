package com.tcs.ilp.servease.repository;

import com.tcs.ilp.servease.entity.TechSkills;
import com.tcs.ilp.servease.entity.TechSkillsId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechSkillsRepository
        extends JpaRepository<TechSkills, TechSkillsId> {

    //  Get all skills of a technician
    List<TechSkills> findByIdTechId(String techId);

    //  Delete all skills of a technician (useful for update)
    void deleteByIdTechId(String techId);
    
    boolean existsByIdTechIdAndIdSkillId(String techId, String skillId);

}
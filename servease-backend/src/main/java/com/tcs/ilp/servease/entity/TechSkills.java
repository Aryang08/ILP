package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "technician_skills", schema = "dev")
public class TechSkills implements Serializable {

    // =========================
    // COMPOSITE PRIMARY KEY 
    // =========================
    @EmbeddedId
    private TechSkillsId id;

    // =========================
    // CONSTRUCTORS
    // =========================
    public TechSkills() {}

    public TechSkills(String techId, String skillId) {
        this.id = new TechSkillsId(techId, skillId);
    }

    // =========================
    // GETTERS & SETTERS
    // =========================
    public String getTechId() {
        return id.getTechId();
    }

    public void setTechId(String techId) {
        this.id.setTechId(techId);
    }

    public String getSkillId() {
        return id.getSkillId();
    }

    public void setSkillId(String skillId) {
        this.id.setSkillId(skillId);
    }
}
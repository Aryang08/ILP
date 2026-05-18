package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TechSkillsId implements Serializable {

    @Column(name = "technician_id")
    private String techId;

    @Column(name = "skill_id")
    private String skillId;

    public TechSkillsId() {}

    public TechSkillsId(String techId, String skillId) {
        this.techId = techId;
        this.skillId = skillId;
    }

    // =========================
    // GETTERS & SETTERS
    // =========================
    public String getTechId() {
        return techId;
    }

    public void setTechId(String techId) {
        this.techId = techId;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    // =========================
    // IMPORTANT (REQUIRED BY JPA)
    // =========================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TechSkillsId)) return false;
        TechSkillsId that = (TechSkillsId) o;
        return Objects.equals(techId, that.techId) &&
               Objects.equals(skillId, that.skillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(techId, skillId);
    }
}
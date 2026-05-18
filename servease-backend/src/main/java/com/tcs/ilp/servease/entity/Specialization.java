package com.tcs.ilp.servease.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "specialization", schema = "dev")
public class Specialization {

    @Id
    @Column(name = "skill_id")
    private String skillId;

    @Column(name = "specialization")
    private String specialization;

    public Specialization() {}

    public Specialization(String skillId, String specialization) {
        this.skillId = skillId;
        this.specialization = specialization;
    }

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}

package com.project.swapskill_backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;
import java.util.UUID;

@Entity
public class SkillsModel {
    @Id
    private UUID skillId;
    private String skillName;

    @ManyToMany(mappedBy = "skills")
    private List<UserSkillRatingModel> userProfiles;

    public UUID getSkillId() {
        return skillId;
    }

    public void setSkillId(UUID skillId) {
        this.skillId = skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public List<UserSkillRatingModel> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(List<UserSkillRatingModel> userProfiles) {
        this.userProfiles = userProfiles;
    }
}

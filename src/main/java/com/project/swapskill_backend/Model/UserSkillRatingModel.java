package com.project.swapskill_backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.UUID;

@Entity
public class UserSkillRatingModel {
    @Id
    private UUID userSkillRatingId;

    // The user
    @ManyToOne
    @JoinColumn(name = "profileId")
    private UserProfileModel users;

    // The skill
    @ManyToOne
    @JoinColumn(name = "skillId")
    private SkillsModel skills;

    private Integer rating;

    public UUID getUserSkillRatingId() {
        return userSkillRatingId;
    }

    public void setUserSkillRatingId(UUID userSkillRatingId) {
        this.userSkillRatingId = userSkillRatingId;
    }

    public UserProfileModel getUsers() {
        return users;
    }

    public void setUsers(UserProfileModel users) {
        this.users = users;
    }

    public SkillsModel getSkills() {
        return skills;
    }

    public void setSkills(SkillsModel skills) {
        this.skills = skills;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}

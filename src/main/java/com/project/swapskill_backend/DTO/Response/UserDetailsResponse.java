package com.project.swapskill_backend.DTO.Response;

import com.project.swapskill_backend.Pojos.KeyValueMapper;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;
import java.util.Map;

public class UserDetailsResponse {
    private String fullName;
    private String profilePhoto;
    private String currentJob;
    private String bio;
    private List<KeyValueMapper<String, Integer>>skillsAndRating;
    private List<String> availableDays;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<KeyValueMapper<String, Integer>> getSkillsAndRating() {
        return skillsAndRating;
    }

    public void setSkillsAndRating(List<KeyValueMapper<String, Integer>> skillsAndRating) {
        this.skillsAndRating = skillsAndRating;
    }

    public List<String> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(List<String> availableDays) {
        this.availableDays = availableDays;
    }
}

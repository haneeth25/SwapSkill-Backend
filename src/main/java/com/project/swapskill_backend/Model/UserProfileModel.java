package com.project.swapskill_backend.Model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Entity
public class UserProfileModel {
    @Id
    private UUID profileId;
    private String fullName;
    private String profilePhoto;
    private String currentJob;
    private String bio;
    private String userRating;

    @OneToOne
    @JoinColumn(name = "id")
    private UserAuthenticationModel userAuthenticationModel;

    @OneToMany(mappedBy = "users")
    private List<UserSkillRatingModel> userSkillRatings;


    @ManyToMany
    @JoinTable(
            name = "user_availability",
            joinColumns = @JoinColumn(name = "profileId"),
            inverseJoinColumns = @JoinColumn(name = "availableDayId")
    )
    private Set<AvailableDayModel> availableDays;




    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public Set<AvailableDayModel> getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(Set<AvailableDayModel> availableDays) {
        this.availableDays = availableDays;
    }

    public List<UserSkillRatingModel> getUserSkillRatings() {
        return userSkillRatings;
    }

    public void setUserSkillRatings(List<UserSkillRatingModel> userSkillRatings) {
        this.userSkillRatings = userSkillRatings;
    }

    public UserAuthenticationModel getUserAuthenticationModel() {
        return userAuthenticationModel;
    }

    public void setUserAuthenticationModel(UserAuthenticationModel userAuthenticationModel) {
        this.userAuthenticationModel = userAuthenticationModel;
    }
}

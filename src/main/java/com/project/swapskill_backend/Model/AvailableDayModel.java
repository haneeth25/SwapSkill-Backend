package com.project.swapskill_backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;


import java.util.Set;
import java.util.UUID;

@Entity
public class AvailableDayModel {
    @Id
    private UUID availableDayId;
    private String availableDay;

    @ManyToMany(mappedBy = "availableDays")
    private Set<UserProfileModel> userProfiles;

    public UUID getAvailableDayId() {
        return availableDayId;
    }

    public void setAvailableDayId(UUID availableDayId) {
        this.availableDayId = availableDayId;
    }

    public String getAvailableDay() {
        return availableDay;
    }

    public void setAvailableDay(String availableDay) {
        this.availableDay = availableDay;
    }

    public Set<UserProfileModel> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(Set<UserProfileModel> userProfiles) {
        this.userProfiles = userProfiles;
    }
}

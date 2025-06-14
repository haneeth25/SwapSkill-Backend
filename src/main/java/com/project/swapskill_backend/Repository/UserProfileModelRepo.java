package com.project.swapskill_backend.Repository;

import com.project.swapskill_backend.Model.UserProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserProfileModelRepo extends JpaRepository<UserProfileModel, UUID> {
    @Query("select e from UserProfileModel e where e.profileId=:profileId")
    UserProfileModel findByProfileId(UUID profileId);
    @Query("select e from UserProfileModel e where e.userAuthenticationModel.id=:userId")
    UserProfileModel findByUserId(UUID userId);

}

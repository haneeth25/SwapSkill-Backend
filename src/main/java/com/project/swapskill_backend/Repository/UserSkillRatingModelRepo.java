package com.project.swapskill_backend.Repository;

import com.project.swapskill_backend.Model.UserSkillRatingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserSkillRatingModelRepo extends JpaRepository<UserSkillRatingModel, UUID> {

}

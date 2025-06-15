package com.project.swapskill_backend.Repository;

import com.project.swapskill_backend.Model.SkillsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SkillsModelRepo extends JpaRepository<SkillsModel, UUID> {

    @Query("select s from SkillsModel s where s.skillName = :skillName")
    SkillsModel findBySkillName(String skillName);

}

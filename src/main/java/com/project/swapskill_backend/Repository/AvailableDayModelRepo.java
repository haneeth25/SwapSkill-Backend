package com.project.swapskill_backend.Repository;

import com.project.swapskill_backend.Model.AvailableDayModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AvailableDayModelRepo extends JpaRepository<AvailableDayModel, UUID> {

    @Query("SELECT a FROM AvailableDayModel a where a.availableDay = :day")
    AvailableDayModel findByDay(String day);
}

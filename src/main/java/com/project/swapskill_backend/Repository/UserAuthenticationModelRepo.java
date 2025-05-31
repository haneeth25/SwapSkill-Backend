package com.project.swapskill_backend.Repository;

import com.project.swapskill_backend.Model.UserAuthenticationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserAuthenticationModelRepo extends JpaRepository<UserAuthenticationModel, UUID> {
    UserAuthenticationModel findByUsername(String username);
}

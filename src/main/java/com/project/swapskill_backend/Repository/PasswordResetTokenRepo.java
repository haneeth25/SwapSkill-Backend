package com.project.swapskill_backend.Repository;

import com.project.swapskill_backend.DTO.PasswordResetToken;
import com.project.swapskill_backend.Model.UserAuthenticationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUser(UserAuthenticationModel user);
}

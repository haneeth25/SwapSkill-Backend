package com.project.swapskill_backend.Service.SecurityService;

import com.project.swapskill_backend.DTO.PasswordResetToken;
import com.project.swapskill_backend.Repository.PasswordResetTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class SchedulerTasks {

    @Autowired
    PasswordResetTokenRepo passwordResetTokenRepo;

    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void deletePasswordTokens() {
        List<PasswordResetToken> tokens =  passwordResetTokenRepo.findAll();
        passwordResetTokenRepo.deleteAll(tokens.stream().filter(item->item.getExpiryDateTime().isBefore(LocalDateTime.now())).toList());
    }
}

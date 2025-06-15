package com.project.swapskill_backend.Controller;

import com.project.swapskill_backend.DTO.Request.ProfileCreationRequest;
import com.project.swapskill_backend.Service.FeatureService.ProfileCreationService;
import com.project.swapskill_backend.Service.SecurityService.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProfileController {

    @Autowired
    ProfileCreationService profileCreationService;

    @PostMapping("/profilecreation")
    public String createProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ProfileCreationRequest profileCreationRequest
            ){
        String token = authHeader.substring(7);
        String username = JwtService.extractUsername(token);
        log.info("Received request at '/profilecreation' endpoint.");
        return profileCreationService.createProfile(profileCreationRequest,username);
    }
}

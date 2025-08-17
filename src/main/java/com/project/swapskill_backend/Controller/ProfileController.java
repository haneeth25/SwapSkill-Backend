package com.project.swapskill_backend.Controller;

import com.project.swapskill_backend.DTO.Request.ProfileCreationRequest;
import com.project.swapskill_backend.DTO.Response.UserDetailsResponse;
import com.project.swapskill_backend.Model.AvailableDayModel;
import com.project.swapskill_backend.Model.UserAuthenticationModel;
import com.project.swapskill_backend.Model.UserProfileModel;
import com.project.swapskill_backend.Model.UserSkillRatingModel;
import com.project.swapskill_backend.Pojos.KeyValueMapper;
import com.project.swapskill_backend.Repository.UserAuthenticationModelRepo;
import com.project.swapskill_backend.Repository.UserProfileModelRepo;
import com.project.swapskill_backend.Repository.UserSkillRatingModelRepo;
import com.project.swapskill_backend.Service.FeatureService.ProfileCreationService;
import com.project.swapskill_backend.Utils.SwapSkillUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
public class ProfileController {

    @Autowired
    ProfileCreationService profileCreationService;

    @Autowired
    UserAuthenticationModelRepo userAuthenticationModelRepo;

    @Autowired
    UserProfileModelRepo userProfileModelRepo;

    @Autowired
    UserSkillRatingModelRepo userSkillRatingModelRepo;

    @PostMapping("/profileCreation")
    public String createProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody ProfileCreationRequest profileCreationRequest
            ){
        String username = SwapSkillUtils.getUsername(authHeader);
        log.info("Received request at '/profileCreation' endpoint.");
        return profileCreationService.createProfile(profileCreationRequest,username);
    }

    @GetMapping("/userDetails")
    public UserDetailsResponse getUserDetails(
            @RequestHeader("Authorization") String authHeader
    ){
        String username = SwapSkillUtils.getUsername(authHeader);
        log.info("Received request at '/userDetails' endpoint.");
        return profileCreationService.getDetailsResponse(username);
    }
}

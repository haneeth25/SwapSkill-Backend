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
        UUID userId=userAuthenticationModelRepo.findByUsername(username).getId();
        log.info("Received request at '/userDetails' endpoint");
        UserProfileModel profileCreationRequest = userProfileModelRepo.findByUserId(userId);
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setFullName(profileCreationRequest.getFullName());
        userDetailsResponse.setCurrentJob(profileCreationRequest.getCurrentJob());
        userDetailsResponse.setBio(profileCreationRequest.getBio());

        List<UserSkillRatingModel> userSkillRatings = profileCreationRequest.getUserSkillRatings();

        List<KeyValueMapper<String, Integer>> finalSkillsAndRating = new ArrayList<>();

        for (UserSkillRatingModel ratingModel : userSkillRatings) {
            String skillName = ratingModel.getSkills().getSkillName(); // Assuming SkillsModel has getSkillName()
            Integer rating = ratingModel.getRating();

            KeyValueMapper<String, Integer> keyValueMapper = new KeyValueMapper<>();
            keyValueMapper.setKey(skillName);
            keyValueMapper.setValue(rating);
            finalSkillsAndRating.add(keyValueMapper);
        }
        userDetailsResponse.setSkillsAndRating(finalSkillsAndRating);

        Set<AvailableDayModel> availableDaysSet = profileCreationRequest.getAvailableDays();
        List<String> availableDays = new ArrayList<>();
        for (AvailableDayModel day : availableDaysSet) {
            availableDays.add(day.getAvailableDay()); // Assuming AvailableDayModel has a getDay()
        }
        userDetailsResponse.setAvailableDays(availableDays);
        return userDetailsResponse;
    }
}

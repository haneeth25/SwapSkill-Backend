package com.project.swapskill_backend.Controller;

import com.project.swapskill_backend.DTO.Request.ProfileCreationRequest;
import com.project.swapskill_backend.DTO.Response.UserDetailsResponse;
import com.project.swapskill_backend.Pojos.KeyValueMapper;
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
        log.info("Received request at '/userDetails' endpoint");
        ProfileCreationRequest profileCreationRequest = new ProfileCreationRequest();
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setFullName("Haneeth Kosaraju");
        userDetailsResponse.setCurrentJob("Software developer");
        userDetailsResponse.setBio("Hi I amm haneeth");
        Map<String,Integer> skillsAndRating = new HashMap<>();
        skillsAndRating.put("Python",3);
        skillsAndRating.put("Java",4);
        List<KeyValueMapper<String , Integer>> finalSkillsAndRating = new ArrayList<>();
        for(Map.Entry<String , Integer> entry : skillsAndRating.entrySet()){
            KeyValueMapper keyValueMapper = new KeyValueMapper();
            keyValueMapper.setKey(entry.getKey());
            keyValueMapper.setValue(entry.getValue());
            finalSkillsAndRating.add(keyValueMapper);
        }
        List<String> availableDays = Arrays.asList("monday","tuesday");
        userDetailsResponse.setAvailableDays(availableDays);
        userDetailsResponse.setSkillsAndRating(finalSkillsAndRating);
        return userDetailsResponse;
    }
}

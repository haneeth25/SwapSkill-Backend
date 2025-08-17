package com.project.swapskill_backend.Service.FeatureService;

import com.project.swapskill_backend.DTO.Request.ProfileCreationRequest;
import com.project.swapskill_backend.DTO.Response.UserDetailsResponse;
import com.project.swapskill_backend.Model.*;
import com.project.swapskill_backend.Pojos.KeyValueMapper;
import com.project.swapskill_backend.Repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProfileCreationService {

    @Autowired
    AvailableDayModelRepo availableDayModelRepo;
    @Autowired
    SkillsModelRepo skillsModelRepo;
    @Autowired
    UserProfileModelRepo userProfileModelRepo;
    @Autowired
    UserAuthenticationModelRepo userAuthenticationModelRepo;
    @Autowired
    UserSkillRatingModelRepo userSkillRatingModelRepo;

    @Transactional
    public String createProfile(ProfileCreationRequest profileCreationRequest , String userName){
        log.info("Started profile creation for :"+userName);
        UserProfileModel userProfileModel = new UserProfileModel();
        userProfileModel.setProfileId(UUID.randomUUID());
        userProfileModel.setFullName(profileCreationRequest.getFullName());
        userProfileModel.setProfilePhoto(profileCreationRequest.getProfilePhoto());
        userProfileModel.setCurrentJob(profileCreationRequest.getCurrentJob());
        userProfileModel.setBio(profileCreationRequest.getBio());

        // Mapping user with authentication model
        UserAuthenticationModel userAuthenticationModel = userAuthenticationModelRepo.findByUsername(userName);
        if(userAuthenticationModel == null){
            log.info("User " + userName +" doesn't exist");
            return "User doesn't exist";
        }
        else{
            userProfileModel.setUserAuthenticationModel(userAuthenticationModel);
        }

        //Mapping User with available days
        Set<AvailableDayModel> availableDays= profileCreationRequest.getAvailableDays().stream().map(day -> {
            AvailableDayModel availableDay = availableDayModelRepo.findByDay(day);
            if(availableDay == null){
                AvailableDayModel availableDayModel = new AvailableDayModel();
                availableDayModel.setAvailableDayId(UUID.randomUUID());
                availableDayModel.setAvailableDay(day);
                return availableDayModelRepo.save(availableDayModel);
            }
            else{
                return availableDay;
            }
        }).collect(Collectors.toSet());

        //adding available days
        userProfileModel.setAvailableDays(availableDays);
        UserProfileModel savedUserProfile = userProfileModelRepo.save(userProfileModel);


        //Mapping User,skill and rating
        List<UserSkillRatingModel> skillRatingModels= profileCreationRequest.getSkillsAndRating().entrySet().stream().map(entry -> {
            SkillsModel skillsModel = skillsModelRepo.findBySkillName(entry.getKey());
            if(skillsModel == null){
                SkillsModel newSkillsModel = new SkillsModel();
                newSkillsModel.setSkillId(UUID.randomUUID());
                newSkillsModel.setSkillName(entry.getKey().toLowerCase());
                SkillsModel savedSkillModel = skillsModelRepo.save(newSkillsModel);
                UserSkillRatingModel userSkillRatingModel = new UserSkillRatingModel();
                userSkillRatingModel.setUserSkillRatingId(UUID.randomUUID());
                userSkillRatingModel.setSkills(savedSkillModel);
                userSkillRatingModel.setUsers(savedUserProfile);
                userSkillRatingModel.setRating(entry.getValue());
                return userSkillRatingModelRepo.save(userSkillRatingModel);


            }
            else{
                UserSkillRatingModel userSkillRatingModel = new UserSkillRatingModel();
                userSkillRatingModel.setUserSkillRatingId(UUID.randomUUID());
                userSkillRatingModel.setSkills(skillsModel);
                userSkillRatingModel.setUsers(savedUserProfile);
                userSkillRatingModel.setRating(entry.getValue());
                return userSkillRatingModelRepo.save(userSkillRatingModel);
            }
        }).toList();

        log.info("Profile created for : "+userName);
        return "Done";
    }

    public UserDetailsResponse getDetailsResponse(String userName){
        UUID userId=userAuthenticationModelRepo.findByUsername(userName).getId();
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
        log.info("Profile details fetched for user : " +userName);
        return userDetailsResponse;
    }
}

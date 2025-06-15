package com.project.swapskill_backend.Service.FeatureService;

import com.project.swapskill_backend.DTO.Request.ProfileCreationRequest;
import com.project.swapskill_backend.Model.*;
import com.project.swapskill_backend.Repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProfileCreationServiceTest {

    @Mock
    AvailableDayModelRepo availableDayModelRepo;
    @Mock
    SkillsModelRepo skillsModelRepo;
    @Mock
    UserProfileModelRepo userProfileModelRepo;
    @Mock
    UserAuthenticationModelRepo userAuthenticationModelRepo;
    @Mock
    UserSkillRatingModelRepo userSkillRatingModelRepo;

    @InjectMocks
    ProfileCreationService profileCreationService;
    private ProfileCreationRequest profileCreationRequest;
    private UserAuthenticationModel userAuthenticationModel;
    private UserProfileModel savedUserProfile;
    private AvailableDayModel availableDayModel;
    private AvailableDayModel availableDayModelSaveResponse;
    private SkillsModel skillsModelJava;
    private SkillsModel skillsModelPython;

    @BeforeEach
    void setUp(){
        // Setup Profile Creation model
        profileCreationRequest = new ProfileCreationRequest();
        profileCreationRequest.setFullName("John Doe");
        profileCreationRequest.setProfilePhoto("photo.jpg");
        profileCreationRequest.setCurrentJob("Software Developer");
        profileCreationRequest.setBio("Experienced developer");
        profileCreationRequest.setAvailableDays(Arrays.asList("Monday", "Tuesday"));
        Map<String, Integer> skillsAndRating = new HashMap<>();
        skillsAndRating.put("Java", 5);
        skillsAndRating.put("Python", 4);
        profileCreationRequest.setSkillsAndRating(skillsAndRating);

        userAuthenticationModel = new UserAuthenticationModel();
        userAuthenticationModel.setUsername("testuser");

        availableDayModel = new AvailableDayModel();
        availableDayModel.setAvailableDayId(UUID.randomUUID());
        availableDayModel.setAvailableDay("Monday");

        availableDayModelSaveResponse = new AvailableDayModel();
        availableDayModelSaveResponse.setAvailableDayId(UUID.randomUUID());
        availableDayModelSaveResponse.setAvailableDay("Monday");

        savedUserProfile = new UserProfileModel();
        savedUserProfile.setProfileId(UUID.randomUUID());

        skillsModelJava = new SkillsModel();
        skillsModelJava.setSkillId(UUID.randomUUID());
        skillsModelJava.setSkillName("java");
        skillsModelPython = new SkillsModel();
        skillsModelPython.setSkillId(UUID.randomUUID());
        skillsModelPython.setSkillName("python");
    }

    @Test
    void createProfie_Success(){

        String userName = "testuser";
        when( userAuthenticationModelRepo.findByUsername(userName)).thenReturn(userAuthenticationModel);
        when(availableDayModelRepo.findByDay("Monday")).thenReturn(availableDayModel);
        when(availableDayModelRepo.findByDay("Tuesday")).thenReturn(null);
        when(availableDayModelRepo.save(any(AvailableDayModel.class))).thenReturn(availableDayModelSaveResponse);
        when(userProfileModelRepo.save(any(UserProfileModel.class))).thenReturn(savedUserProfile);
        when(skillsModelRepo.findBySkillName("Java")).thenReturn(skillsModelJava);
        when(skillsModelRepo.findBySkillName("Python")).thenReturn(null);
        when(skillsModelRepo.save(any(SkillsModel.class))).thenReturn(skillsModelPython);
        when(userSkillRatingModelRepo.save(any(UserSkillRatingModel.class))).thenReturn(new UserSkillRatingModel());

        String result = profileCreationService.createProfile(profileCreationRequest, userName);

        Assertions.assertEquals(result,"Done");

        verify(userAuthenticationModelRepo).findByUsername(userName);
        verify(availableDayModelRepo, times(2)).findByDay(anyString());
        verify(userProfileModelRepo).save(any(UserProfileModel.class));
        verify(skillsModelRepo, times(2)).findBySkillName(anyString());
        verify(userSkillRatingModelRepo, times(2)).save(any(UserSkillRatingModel.class));
        verify(availableDayModelRepo).save(any(AvailableDayModel.class));
        verify(skillsModelRepo).save(any(SkillsModel.class));
    }


}
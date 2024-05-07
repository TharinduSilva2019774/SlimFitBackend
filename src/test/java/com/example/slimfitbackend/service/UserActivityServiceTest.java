package com.example.slimfitbackend.service;

import com.example.slimfitbackend.model.ActivityType;
import com.example.slimfitbackend.model.DailyCalorie;
import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.model.UserActivity;
import com.example.slimfitbackend.payload.NewActivityRequest;
import com.example.slimfitbackend.payload.NewActivityResponse;
import com.example.slimfitbackend.payload.common.MapStructMapper;
import com.example.slimfitbackend.repository.ActivityTypeRepository;
import com.example.slimfitbackend.repository.DailyCalorieRepository;
import com.example.slimfitbackend.repository.UserActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserActivityServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private DailyCalorieRepository dailyCalorieRepository;

    @Mock
    private ActivityTypeRepository activityTypeRepository;

    @Mock
    private DailyCalorieService dailyCalorieService;

    @Mock
    private UserActivityRepository userActivityRepository;

    @Mock
    private MapStructMapper mapStructMapper;

    @InjectMocks
    private UserActivityService userActivityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNewActivity_ReturnsNewActivityResponse() {
        User user = new User();
        user.setUserId(1L);

        NewActivityRequest newActivityRequest = new NewActivityRequest();
        newActivityRequest.setDate(new Date());
        newActivityRequest.setDuration(60);
        newActivityRequest.setIntensity(2);
        newActivityRequest.setCalorie(300);
        newActivityRequest.setActivityType(1L);

        ActivityType activityType = new ActivityType();
        activityType.setActId(1L);
        activityType.setName("Running");

        UserActivity userActivity = new UserActivity();
        userActivity.setUser(user);
        userActivity.setDate(newActivityRequest.getDate());
        userActivity.setDuration(newActivityRequest.getDuration());
        userActivity.setIntensity(newActivityRequest.getIntensity());
        userActivity.setCalorie(newActivityRequest.getCalorie());
        userActivity.setActivityType(activityType);

        when(userService.getCurrentUser()).thenReturn(user);
        when(activityTypeRepository.findById(newActivityRequest.getActivityType())).thenReturn(Optional.of(activityType));
        when(userActivityRepository.save(any(UserActivity.class))).thenReturn(userActivity);
        when(dailyCalorieService.getDailyCalorie(any(),any())).thenReturn(new DailyCalorie());

        NewActivityResponse expectedResponse = new NewActivityResponse();
        expectedResponse.setDate(userActivity.getDate());
        expectedResponse.setDuration(userActivity.getDuration());
        expectedResponse.setIntensity(userActivity.getIntensity());
        expectedResponse.setCalorie(userActivity.getCalorie());

        when(mapStructMapper.userActivityToNewActivityResponse(userActivity)).thenReturn(expectedResponse);

        NewActivityResponse actualResponse = userActivityService.addNewActivity(newActivityRequest);

        assertEquals(expectedResponse, actualResponse);
    }

}

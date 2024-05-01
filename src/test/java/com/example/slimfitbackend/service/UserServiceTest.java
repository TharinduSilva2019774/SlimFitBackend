package com.example.slimfitbackend.service;

import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.model.WeightProgress;
import com.example.slimfitbackend.payload.GetUserResponse;
import com.example.slimfitbackend.payload.SaveUserWeightRequest;
import com.example.slimfitbackend.payload.UserWeightResponse;
import com.example.slimfitbackend.payload.SaveUserRequest;
import com.example.slimfitbackend.payload.common.MapStructMapper;
import com.example.slimfitbackend.repository.UserRepository;
import com.example.slimfitbackend.repository.WeightProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private MapStructMapper mapStructMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WeightProgressRepository weightProgressRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNewUser() {
        // Given
        SaveUserRequest saveUserRequest = new SaveUserRequest();
        saveUserRequest.setDateOfBirth(new Date());
        saveUserRequest.setHeight(170);
        saveUserRequest.setWeight(70);
        saveUserRequest.setGender(1);
        saveUserRequest.setWeeklyWeightLossGoal(500);
        saveUserRequest.setDailyActivityGoal(500);

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // When
        boolean result = userService.createNewUser(saveUserRequest);

        // Then
        assertEquals(true, result);
    }

    @Test
    void testGetUser() throws Exception {
        // Given
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(mapStructMapper.userToGetUserResponse(any(User.class))).thenReturn(new GetUserResponse());

        // When
        GetUserResponse result = userService.getUser();

        // Then
        assertEquals(new GetUserResponse(), result);
    }

    @Test
    void testGetUserWeight() throws Exception {
        // Given
        User user = new User();
        user.setEmail("test@example.com");

        List<WeightProgress> weightProgressList = new ArrayList<>();
        WeightProgress weightProgress = new WeightProgress();
        weightProgress.setWeight(70);
        weightProgressList.add(weightProgress);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(weightProgressRepository.findAllByUserOrderByDate(any(User.class))).thenReturn(weightProgressList);

        // When
        UserWeightResponse result = userService.getUserWeight();

        // Then
        assertEquals(70, result.getWeight());
    }

    @Test
    void testSaveUserWeight() throws Exception {
        // Given
        SaveUserWeightRequest saveUserWeightRequest = new SaveUserWeightRequest();
        saveUserWeightRequest.setWeight(70);

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        // When
        UserWeightResponse result = userService.saveUserWeight(saveUserWeightRequest);

        // Then
        assertEquals(70, result.getWeight());
    }
}

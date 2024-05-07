package com.example.slimfitbackend.service;

import com.example.slimfitbackend.model.DailyCalorie;
import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.model.WeightProgress;
import com.example.slimfitbackend.payload.DailyCalorieResponseDto;
import com.example.slimfitbackend.payload.GetDailyCalorieDto;
import com.example.slimfitbackend.payload.common.MapStructMapper;
import com.example.slimfitbackend.repository.DailyCalorieRepository;
import com.example.slimfitbackend.repository.UserActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DailyCalorieServiceTest {

    @Mock
    private DailyCalorieRepository dailyCalorieRepository;

    @Mock
    private UserService userService;

    @Mock
    private MapStructMapper mapStructMapper;

    @Mock
    private UserActivityRepository userActivityRepository;

    @InjectMocks
    private DailyCalorieService dailyCalorieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetDailyCalorieResponse() throws Exception {
        // Mocking user
        User user = new User();
        user.setUserId(1L);
        user.setBmr(2000);
        user.setDailyActivityGoal(500);
        user.setWeeklyWeightLossGoal(1000);

        // Mocking current date
        Date currentDate = new Date();

        // Mocking user service to return current user
        when(userService.getCurrentUser()).thenReturn(user);

        // Mocking daily calorie response DTO
        DailyCalorieResponseDto mockedResponseDto = new DailyCalorieResponseDto();
        mockedResponseDto.setDailyGoal(1800);
        mockedResponseDto.setCurrentWeight(70);
        mockedResponseDto.setTargetWeight(65);
        mockedResponseDto.setTotalActiveMinutes(60);

        WeightProgress weightProgress = new WeightProgress();
        weightProgress.setWeight(10.0);

        // Mocking daily calorie repository
        when(dailyCalorieRepository.findByDateAndUser(any(Date.class), eq(user))).thenReturn(Optional.empty());
        DailyCalorie newDailyCalorie = new DailyCalorie();
        when(dailyCalorieRepository.save(any(DailyCalorie.class))).thenReturn(newDailyCalorie);
        when(mapStructMapper.dailyCalorietoDailyCalorieResponseDto(any())).thenReturn(mockedResponseDto);
        when(userService.getUserWeight()).thenReturn(weightProgress);

        // Test
        GetDailyCalorieDto getDailyCalorieDto = new GetDailyCalorieDto();
        getDailyCalorieDto.setDate(currentDate);
        DailyCalorieResponseDto responseDto = dailyCalorieService.getDailyCalorieResponse(getDailyCalorieDto);

        mockedResponseDto.setCurrentWeight(weightProgress.getWeight());
        mockedResponseDto.setTotalActiveMinutes(0);

        // Verify
        assertEquals(mockedResponseDto, responseDto);
        verify(userService, times(1)).getCurrentUser();
        verify(dailyCalorieRepository, times(1)).findByDateAndUser(any(Date.class), eq(user));
        verify(dailyCalorieRepository, times(1)).save(any(DailyCalorie.class));
    }

}

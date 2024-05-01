package com.example.slimfitbackend.service;

import com.example.slimfitbackend.model.DailyCalorie;
import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.payload.DailyCalorieResponseDto;
import com.example.slimfitbackend.payload.GetDailyCalorieDto;
import com.example.slimfitbackend.payload.common.MapStructMapper;
import com.example.slimfitbackend.repository.DailyCalorieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DailyCalorieServiceTest {

    @Mock
    private DailyCalorieRepository dailyCalorieRepository;

    @Mock
    private UserService userService;

    @Mock
    private MapStructMapper mapStructMapper;

    @InjectMocks
    private DailyCalorieService dailyCalorieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDailyCalorieResponse() throws Exception {
        // Given
        GetDailyCalorieDto getDailyCalorieDto = new GetDailyCalorieDto();
        getDailyCalorieDto.setDate(new Date());

        User user = new User();
        user.setWeeklyWeightLossGoal(500); // Assuming 500 grams per week
        user.setDailyActivityGoal(2000); // Assuming daily activity goal
        user.setBmr(1800); // Assuming Basal Metabolic Rate

        DailyCalorie dailyCalorie = new DailyCalorie();
        dailyCalorie.setUser(user);
        dailyCalorie.setDate(new Date());
        // Assuming some values for actual and goal calories
        dailyCalorie.setDailyGoal(1800);
        dailyCalorie.setDailyActual(1600);
        dailyCalorie.setDailyActivityGoal(2000);
        dailyCalorie.setDailyActivityActual(1800);
        dailyCalorie.setBreakfastGoal(400);
        dailyCalorie.setBreakfastActual(300);
        dailyCalorie.setLunchGoal(600);
        dailyCalorie.setLunchActual(500);
        dailyCalorie.setDinnerGoal(600);
        dailyCalorie.setDinnerActual(500);
        dailyCalorie.setSnackGoal(200);
        dailyCalorie.setSnackActual(100);

        when(userService.getCurrentUser()).thenReturn(user);
        when(dailyCalorieRepository.findByDateAndUser(any(Date.class), any(User.class))).thenReturn(Optional.of(dailyCalorie));
        DailyCalorieResponseDto returnObj = new DailyCalorieResponseDto();
        returnObj.setDailyActual(dailyCalorie.getDailyActual());
        when(mapStructMapper.dailyCalorietoDailyCalorieResponseDto(any(DailyCalorie.class))).thenReturn(returnObj);

        // When
        DailyCalorieResponseDto result = dailyCalorieService.getDailyCalorieResponse(getDailyCalorieDto);

        // Then
        assertEquals(dailyCalorie.getDailyActual(), result.getDailyActual());
    }
}

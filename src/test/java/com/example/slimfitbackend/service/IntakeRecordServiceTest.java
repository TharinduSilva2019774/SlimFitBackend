package com.example.slimfitbackend.service;

import com.example.slimfitbackend.model.DailyCalorie;
import com.example.slimfitbackend.model.IntakeRecord;
import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.payload.*;
import com.example.slimfitbackend.payload.common.MapStructMapper;
import com.example.slimfitbackend.repository.IntakeRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class IntakeRecordServiceTest {

    @Mock
    private DailyCalorieService dailyCalorieService;

    @Mock
    private UserService userService;

    @Mock
    private IntakeRecordRepository intakeRecordRepository;

    @Mock
    private MapStructMapper mapStructMapper;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private IntakeRecordService intakeRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveNewIntakeRecord_Success() throws Exception {
        IntakeRecordRequest request = new IntakeRecordRequest();
        request.setCalorieCount(200);
        request.setMealName("Breakfast");
        request.setNote("Test note");
        request.setDate(new Date());
        request.setMeal(1);

        User user = new User(); // create a mock user

        DailyCalorie dailyCalorie = new DailyCalorie();
        dailyCalorie.setBreakfastActual(0);
        dailyCalorie.setLunchActual(0);
        dailyCalorie.setDinnerActual(0);
        dailyCalorie.setSnackActual(0);
        dailyCalorie.setDailyActual(0);

        when(userService.getCurrentUser()).thenReturn(user);
        when(dailyCalorieService.getDailyCalorie(user, request.getDate())).thenReturn(dailyCalorie);

        IntakeRecord savedIntakeRecord = new IntakeRecord();


        when(intakeRecordRepository.save(any())).thenReturn(savedIntakeRecord);
        IntakeRecordResponse expectedResponse = new IntakeRecordResponse();
        when(mapStructMapper.itakeRecordToIntakeRecordResponse(any())).thenReturn(expectedResponse);

        IntakeRecordResponse actualResponse = intakeRecordService.saveNewIntakeRecord(request);

        assertEquals(expectedResponse, actualResponse);
        assertEquals(200, dailyCalorie.getBreakfastActual());
        assertEquals(200, dailyCalorie.getDailyActual());
    }
}

package com.example.slimfitbackend.service;

import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.model.WeightProgress;
import com.example.slimfitbackend.payload.UserWeightResponse;
import com.example.slimfitbackend.repository.WeightProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgressServiceTest {

    @Mock
    private WeightProgressRepository weightProgressRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProgressService progressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getActualWeightProgress_ReturnsUserWeightResponseList() throws Exception {
        User user = new User();
        user.setUserId(1L);

        WeightProgress weightProgress1 = new WeightProgress();
        weightProgress1.setWeight(70.0);
        weightProgress1.setDate(new Date());

        WeightProgress weightProgress2 = new WeightProgress();
        weightProgress2.setWeight(68.5);
        weightProgress2.setDate(new Date());

        List<WeightProgress> weightProgressList = new ArrayList<>();
        weightProgressList.add(weightProgress1);
        weightProgressList.add(weightProgress2);

        when(userService.getCurrentUser()).thenReturn(user);
        when(weightProgressRepository.findAllByUserOrderByDate(user)).thenReturn(weightProgressList);

        List<UserWeightResponse> actualWeightProgress = progressService.getActualWeightProgress();

        assertEquals(2, actualWeightProgress.size());
        assertEquals(70.0, actualWeightProgress.get(0).getWeight());
        assertEquals(68.5, actualWeightProgress.get(1).getWeight());
    }

    @Test
    void getUserGoal_ReturnsUserWeightResponseList() throws Exception {
        User user = new User();

        LocalDate today = LocalDate.now();
        LocalDate oneWeekAgo = today.minusWeeks(1);
        LocalDate untilDate = oneWeekAgo.minusDays(1);
        Date untilUtilDate = Date.from(untilDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        user.setStartDate(untilUtilDate);
        user.setStartWeight(75.0);
        user.setWeeklyWeightLossGoal(500);

        when(userService.getCurrentUser()).thenReturn(user);

        List<UserWeightResponse> userGoal = progressService.getUserGoal();

        assertEquals(2, userGoal.size());
        assertEquals(75.0, userGoal.get(0).getWeight());

        assertEquals(74.5, userGoal.get(1).getWeight());
    }

}

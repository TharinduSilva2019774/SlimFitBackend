package com.example.slimfitbackend.service;


import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.model.WeightProgress;
import com.example.slimfitbackend.payload.UserWeightResponse;
import com.example.slimfitbackend.repository.WeightProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class ProgressService {

    @Autowired
    private WeightProgressRepository weightProgressRepository;

    @Autowired
    private UserService userService;

    public List<UserWeightResponse> getActualWeightProgress() throws Exception {
        User user = userService.getCurrentUser();
        List<WeightProgress> weightProgresses = weightProgressRepository.findAllByUserOrderByDate(user);
        List<UserWeightResponse> userWeightResponses = new ArrayList<>();
        for (WeightProgress weightProgress : weightProgresses) {
            userWeightResponses.add(new UserWeightResponse(weightProgress.getWeight(), weightProgress.getDate()));
        }
        return userWeightResponses;
    }

    public List<UserWeightResponse> getUserGoal() throws Exception {
        User user = userService.getCurrentUser();
        List<Date> dateList = generateDateListPerWeek(user.getStartDate(), new Date());
        List<UserWeightResponse> userWeightResponses = new ArrayList<>();
        double currentWeight = user.getStartWeight();
        for (Date date : dateList) {
            userWeightResponses.add(new UserWeightResponse(currentWeight, date));
            currentWeight = ((currentWeight * 1000) - user.getWeeklyWeightLossGoal()) / 1000;
        }
        return userWeightResponses;
    }

    public static List<Date> generateDateListPerWeek(Date startDate, Date endDate) {
        List<Date> dateList = new ArrayList<>();

        // Convert input Date objects to LocalDate
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate currentDate = startLocalDate;

        while (currentDate.isBefore(endLocalDate)) {
            // Convert LocalDate back to Date
            dateList.add(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            currentDate = currentDate.plusWeeks(1);
        }

        return dateList;
    }
}

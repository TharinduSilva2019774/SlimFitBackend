package com.example.slimfitbackend.service;

import com.example.slimfitbackend.model.DailyCalorie;
import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.model.UserActivity;
import com.example.slimfitbackend.payload.DailyCalorieResponseDto;
import com.example.slimfitbackend.payload.GetDailyCalorieDto;
import com.example.slimfitbackend.payload.common.MapStructMapper;
import com.example.slimfitbackend.repository.DailyCalorieRepository;
import com.example.slimfitbackend.repository.UserActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Calendar;

@Service
public class DailyCalorieService {

    @Autowired
    private DailyCalorieRepository dailyCalorieRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MapStructMapper mapStructMapper;

    @Autowired
    private UserActivityRepository userActivityRepository;

    private int calorieDeficitPerToday(int weightLossPerWeekInG) {

        double weightLossPerWeekInKG = (double) weightLossPerWeekInG / 1000;

        int oneKGFat = 7700;
        return (int) ((weightLossPerWeekInKG * oneKGFat) / 7);
    }

    private void devideCaloriesForMeals(int calorieIntakePerDay, DailyCalorie dailyCalorie) {
        // breakfast 25
        // lunch 30
        // dinner 30
        // snack 15
        dailyCalorie.setBreakfastGoal((long) (calorieIntakePerDay * (25f / 100f)));
        dailyCalorie.setBreakfastActual(0);

        dailyCalorie.setLunchGoal((long) (calorieIntakePerDay * (30f / 100f)));
        dailyCalorie.setLunchActual(0);

        dailyCalorie.setDinnerGoal((long) (calorieIntakePerDay * (30f / 100f)));
        dailyCalorie.setDinnerActual(0);

        dailyCalorie.setSnackGoal((long) (calorieIntakePerDay * (15f / 100f)));
        dailyCalorie.setSnackActual(0);

        dailyCalorie.setDailyGoal(calorieIntakePerDay);
        dailyCalorie.setDailyActual(0);
    }

    public DailyCalorieResponseDto getDailyCalorieResponse(GetDailyCalorieDto getDailyCalorieDto) throws Exception {
        User user = userService.getCurrentUser();
        Optional<DailyCalorie> optDailyCal = dailyCalorieRepository.findByDateAndUser(getDailyCalorieDto.getDate(), user);
        DailyCalorie dailyCalorie;
        if (optDailyCal.isEmpty()) {
            dailyCalorie = createNewDailyCalorie(user, getDailyCalorieDto.getDate());
            dailyCalorieRepository.save(dailyCalorie);
            DailyCalorieResponseDto returnObject = mapStructMapper.dailyCalorietoDailyCalorieResponseDto(dailyCalorie);
            returnObject.setCurrentWeight(userService.getUserWeight().getWeight());
            returnObject.setTargetWeight(user.getTargetWeight());
            returnObject.setTotalActiveMinutes(getDailyActiveMinutes(getDailyCalorieDto.getDate(),user));
            return returnObject;
        }
        // get from db comparing date if not found create
        DailyCalorieResponseDto returnObject = mapStructMapper.dailyCalorietoDailyCalorieResponseDto(optDailyCal.get());
        returnObject.setCurrentWeight(userService.getUserWeight().getWeight());
        returnObject.setTargetWeight(user.getTargetWeight());
        returnObject.setTotalActiveMinutes(getDailyActiveMinutes(getDailyCalorieDto.getDate(),user));
        return returnObject;
    }

    public int getDailyActiveMinutes(Date currentDate, User user){

        // Set the time to midnight  represent the start of the day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date startDate = calendar.getTime();

        // Set the time to just before midnight to represent the end of the day
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date endDate = calendar.getTime();

        List<UserActivity> todayActivities = userActivityRepository.findAllByDateBetweenAndUser(startDate,endDate,user);
        int totalMinutes = 0;
        for (UserActivity todayActivity : todayActivities) {
            totalMinutes += (int) todayActivity.getDuration();
        }
        return totalMinutes;
    }

    public DailyCalorie getDailyCalorie(User user, Date date) {
        Optional<DailyCalorie> dailyOpt = dailyCalorieRepository.findByDateAndUser(date, user);
        DailyCalorie daily;
        daily = dailyOpt.orElseGet(() -> createNewDailyCalorie(user, date));
        return daily;
    }

    private DailyCalorie createNewDailyCalorie(User user, Date date) {
        DailyCalorie dailyCalorie = new DailyCalorie();
        dailyCalorie.setUser(user);
        dailyCalorie.setDate(date);
        int calorieDeficit = calorieDeficitPerToday(user.getWeeklyWeightLossGoal());
        int dailyCalorieIntake = (int) (user.getBmr() - (calorieDeficit - user.getDailyActivityGoal()));
        devideCaloriesForMeals(dailyCalorieIntake, dailyCalorie);
        dailyCalorie.setDailyActivityActual(0);
        dailyCalorie.setDailyActivityGoal(user.getDailyActivityGoal());
        return dailyCalorie;
    }
}

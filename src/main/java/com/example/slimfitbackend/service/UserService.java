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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.example.slimfitbackend.util.Util.yearCountBetweenDates;

@Service
public class UserService {

    @Autowired
    private MapStructMapper mapStructMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeightProgressRepository weightProgressRepository;

    @Transactional
    public boolean createNewUser(SaveUserRequest saveUserRequest) {

        try {
            User user = getCurrentUser();
            user.setAge(yearCountBetweenDates(saveUserRequest.getDateOfBirth(), new Date()));
            user.setDateOfBirth(saveUserRequest.getDateOfBirth());
            user.setHeight(saveUserRequest.getHeight());
            user.setWeight(saveUserRequest.getWeight());
            user.setStartWeight(saveUserRequest.getWeight());
            user.setGender(saveUserRequest.getGender());
            user.setStartDate(new Date());

            //1 == male 2 == female
            if (user.getGender() == 1) {
                user.setBmr(88.362 + (13.397 * user.getWeight()) + (4.799 * user.getHeight()) - (5.677 * user.getAge()));
            } else if (user.getGender() == 2) {
                user.setBmr(447.593 + (9.247 * user.getWeight()) + (3.098 * user.getHeight()) - (4.330 * user.getAge()));
            }
            user.setWeeklyWeightLossGoal(saveUserRequest.getWeeklyWeightLossGoal());

            user.setDailyActivityGoal(saveUserRequest.getDailyActivityGoal());
            userRepository.save(user);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User getCurrentUser() throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> optUser = userRepository.findByEmail(email);
        if (optUser.isPresent()) {
            return optUser.get();
        } else {
            throw new Exception();
        }

    }

    public GetUserResponse getUser() throws Exception {
        return mapStructMapper.userToGetUserResponse(getCurrentUser());
    }


    public UserWeightResponse getUserWeight() throws Exception {
        User user = getCurrentUser();
        List<WeightProgress> userWeightLogs = weightProgressRepository.findAllByUserOrderByDate(user);
        System.out.println(userWeightLogs);
        return new UserWeightResponse(userWeightLogs.get(userWeightLogs.size() - 1).getWeight());
    }

    public UserWeightResponse saveUserWeight(SaveUserWeightRequest saveUserWeightRequest) throws Exception {
        WeightProgress weightProgress = new WeightProgress();
        weightProgress.setWeight(saveUserWeightRequest.getWeight());
        weightProgress.setDate(new Date());
        weightProgress.setUser(getCurrentUser());
        weightProgressRepository.save(weightProgress);
        return new UserWeightResponse(weightProgress.getWeight());
    }
}
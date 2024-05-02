package com.example.slimfitbackend.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SaveUserRequest {

    private Date dateOfBirth;

    private double height;

    private double weight;

    private int gender;

    private int weeklyWeightLossGoal;

    private int dailyActivityGoal;

    private double targetWeight;

}

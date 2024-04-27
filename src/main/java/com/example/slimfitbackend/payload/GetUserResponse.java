package com.example.slimfitbackend.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class GetUserResponse {

    private Long userId;

    private String email;

    private String firstName;

    private String lastName;

    private int age;

    private Date dateOfBirth;

    private double height;

    private double weight;

    private int gender;

    private double bmr;

    private int weeklyWeightLossGoal;

    private int dailyActivityGoal;

}

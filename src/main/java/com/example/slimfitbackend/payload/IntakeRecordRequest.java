package com.example.slimfitbackend.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class IntakeRecordRequest {

    private Date date;

    private long calorieCount;

    private String mealName;

    private String note;

    private long meal;

}

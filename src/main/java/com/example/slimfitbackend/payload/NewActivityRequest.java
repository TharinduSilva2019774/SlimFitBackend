package com.example.slimfitbackend.payload;

import com.example.slimfitbackend.model.ActivityType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class NewActivityRequest {

    private long activityType;

    private long calorie;

    private long duration;

    private long intensity;

    private Date date;

}

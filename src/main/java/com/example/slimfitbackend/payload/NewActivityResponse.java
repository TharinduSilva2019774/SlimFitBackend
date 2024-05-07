package com.example.slimfitbackend.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class NewActivityResponse {

    private long calorie;

    private long duration;

    private long intensity;

    private Date date;

}

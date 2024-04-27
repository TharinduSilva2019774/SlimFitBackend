package com.example.slimfitbackend.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictCalorieRequest {

    private int intensity;
    private Long actId;
    private long duration;

}

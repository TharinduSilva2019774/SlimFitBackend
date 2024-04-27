package com.example.slimfitbackend.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class UserWeightResponse {

    private double weight;

    private Date date;

    public UserWeightResponse(double weight) {
        this.weight = weight;
    }

}

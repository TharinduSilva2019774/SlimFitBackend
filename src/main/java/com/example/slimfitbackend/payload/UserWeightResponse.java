package com.example.slimfitbackend.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserWeightResponse {

    private double weight;

    private Date date;

    public UserWeightResponse(double weight) {
        this.weight = weight;
    }

    public UserWeightResponse(double weight, Date date) {
        this.weight = weight;
        this.date = date;
    }

}

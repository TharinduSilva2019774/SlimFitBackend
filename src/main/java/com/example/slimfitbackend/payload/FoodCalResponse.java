package com.example.slimfitbackend.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class FoodCalResponse {

    private String foodName;
    private double cal;

}

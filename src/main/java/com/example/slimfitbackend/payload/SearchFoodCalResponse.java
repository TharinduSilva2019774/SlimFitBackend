package com.example.slimfitbackend.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SearchFoodCalResponse {

    private FoodCalResponse matchFood;

    private List<FoodCalResponse> hintedFoods;

}

package com.example.slimfitbackend.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MLModelServiceTest {

    @Test
    void getCalorie_ReturnsCorrectCalorieValue() {
        double gender = 1.0;
        double age = 30;
        double height = 175;
        double weight = 70;
        double duration = 60;
        double exerciseTypeNum = 1;
        double intensity = 3;

        double expectedCalorie = 93.86078800514693; // An output value from the model

        double actualCalorie = MLModelService.getCalorie(gender, age, height, weight, duration, exerciseTypeNum, intensity);

        assertEquals(expectedCalorie, actualCalorie, 0.01);
    }
}

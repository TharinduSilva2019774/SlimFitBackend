package com.example.slimfitbackend.payload;

public class UserWeightResponse {

    private double weight;

    public UserWeightResponse(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

package com.example.slimfitbackend.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GetActivitiesResponse {

    private String label;
    private long value;

}

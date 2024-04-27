package com.example.slimfitbackend.controller;

import com.example.slimfitbackend.payload.UserWeightResponse;
import com.example.slimfitbackend.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/progress")
public class ProgressController {

    @Autowired
    private ProgressService progressService;

    @GetMapping("/actual")
    private List<UserWeightResponse> getAllUserWeight() throws Exception {
        return progressService.getActualWeightProgress();
    }

    @GetMapping("/goal")
    private List<UserWeightResponse> getUserGoal() throws Exception {
        return progressService.getUserGoal();
    }
}

package com.example.slimfitbackend.controller;

import com.example.slimfitbackend.payload.GetUserResponse;
import com.example.slimfitbackend.payload.SaveUserWeightRequest;
import com.example.slimfitbackend.payload.UserWeightResponse;
import com.example.slimfitbackend.payload.SaveUserRequest;
import com.example.slimfitbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public boolean createNewUser(@RequestBody SaveUserRequest saveUserRequest) {
        return userService.createNewUser(saveUserRequest);
    }

    @GetMapping("")
    private GetUserResponse getUser() throws Exception {
        return userService.getUser();
    }

    @GetMapping("/weight")
    private UserWeightResponse getUserWeight() throws Exception {
        return userService.getUserWeight();
    }

    @PostMapping("/weight")
    private UserWeightResponse saveUserWeight(@RequestBody SaveUserWeightRequest saveUserWeightRequest) throws Exception {
        return userService.saveUserWeight(saveUserWeightRequest);
    }
}

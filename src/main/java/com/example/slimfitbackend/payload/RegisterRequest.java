package com.example.slimfitbackend.payload;

import com.example.slimfitbackend.model.Role;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
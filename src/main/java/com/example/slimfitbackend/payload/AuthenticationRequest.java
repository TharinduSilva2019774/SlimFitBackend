package com.example.slimfitbackend.payload;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class AuthenticationRequest {

         String email;

         String password;

}

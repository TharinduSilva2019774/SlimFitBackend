package com.example.slimfitbackend.payload;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {

         String email;
         String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

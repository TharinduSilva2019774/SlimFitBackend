package com.example.slimfitbackend.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class MessageResponse {

    private Long id;

    private String message;

    private Date date;

    private String username;

}

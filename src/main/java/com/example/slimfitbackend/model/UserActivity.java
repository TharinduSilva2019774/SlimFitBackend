package com.example.slimfitbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;


@Entity
@Data
@Table(name = "user_activity")
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userActivityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "act_id")
    private ActivityType activityType;

    @NotNull
    private long calorie;

    @NotNull
    private long duration;

    @NotNull
    private long intensity;

    @Past
    @NotNull
    private Date date;

}

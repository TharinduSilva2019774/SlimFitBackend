package com.example.slimfitbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "intake_record")
public class IntakeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long IntakeRecordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_calorie_id")
    private DailyCalorie dailyCalorie;

    @NotNull
    private long calorieCount;

    private String MealName;

    private String note;

    @NotNull
    private long meal;

}

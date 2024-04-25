package com.example.slimfitbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.util.Date;

@Entity
@Data
@EnableAutoConfiguration
@Table(name = "weight_progress")
public class WeightProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weightProgressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private double weight;

    private Date date;

    public Long getWeightProgressId() {
        return weightProgressId;
    }

    public void setWeightProgressId(Long weightProgressId) {
        this.weightProgressId = weightProgressId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

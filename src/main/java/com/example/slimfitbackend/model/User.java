package com.example.slimfitbackend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@EnableAutoConfiguration
@Table(name = "\"user\"")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique=true)
    private String email;

    private String firstName;

    private String lastName;

    private int age;

    private Date dateOfBirth;

    private double height;

    private double weight;

    private int gender;

    private double bmr;

    private int weeklyWeightLossGoal;

    private int dailyActivityGoal;

    private String password;

    private Date startDate;

    private double startWeight;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserActivity> userActivities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WeightProgress> weightProgresses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    public User(String email, String firstName, String lastName, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public User() {
    }

    public User(Long userId, String email, String firstName, String lastName, int age, Date dateOfBirth, double height, double weight, int gender, double bmr, int weeklyWeightLossGoal, int dailyActivityGoal, String password, Role role, List<UserActivity> userActivities, List<WeightProgress> weightProgresses) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.bmr = bmr;
        this.weeklyWeightLossGoal = weeklyWeightLossGoal;
        this.dailyActivityGoal = dailyActivityGoal;
        this.password = password;
        this.role = role;
        this.userActivities = userActivities;
        this.weightProgresses = weightProgresses;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.User.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

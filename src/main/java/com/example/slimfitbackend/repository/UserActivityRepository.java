package com.example.slimfitbackend.repository;

import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.model.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {

    List<UserActivity> findAllByDateBetweenAndUser(Date startDate, Date endDate, User user);
}

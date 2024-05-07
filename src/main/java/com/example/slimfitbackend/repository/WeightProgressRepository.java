package com.example.slimfitbackend.repository;

import com.example.slimfitbackend.model.User;
import com.example.slimfitbackend.model.WeightProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeightProgressRepository extends JpaRepository<WeightProgress, Long> {

    List<WeightProgress> findAllByUserOrderByDate(User user);

}

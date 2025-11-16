package com.fitnessapp.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fitnessapp.fitnessapp.model.WeeklyProgress;
import java.util.List;

public interface WeeklyProgressRepository extends JpaRepository<WeeklyProgress, Integer> {
    @Query("SELECT w FROM WeeklyProgress w WHERE w.user.user_id = :userId")
    List<WeeklyProgress> findByUserId(@Param("userId") Integer userId);
}

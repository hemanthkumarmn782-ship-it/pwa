package com.fitnessapp.fitnessapp.repository;

import com.fitnessapp.fitnessapp.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findByCreatedByUserId(Long userId);
    List<WorkoutPlan> findByLevelAndPlanType(String level, String planType);
}

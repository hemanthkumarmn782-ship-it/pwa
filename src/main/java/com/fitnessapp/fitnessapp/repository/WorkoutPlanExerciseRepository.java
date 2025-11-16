package com.fitnessapp.fitnessapp.repository;

import com.fitnessapp.fitnessapp.model.WorkoutPlanExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkoutPlanExerciseRepository extends JpaRepository<WorkoutPlanExercise, Long> {
    List<WorkoutPlanExercise> findByPlanIdOrderByDayNoAscOrderNoAsc(Long planId);
    List<WorkoutPlanExercise> findByPlanIdAndDayNoOrderByOrderNoAsc(Long planId, Integer dayNo);
}

package com.fitnessapp.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fitnessapp.fitnessapp.model.WorkoutCategory;

public interface WorkoutCategoryRepository extends JpaRepository<WorkoutCategory, Integer> {
}

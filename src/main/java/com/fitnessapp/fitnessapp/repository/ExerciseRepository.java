package com.fitnessapp.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fitnessapp.fitnessapp.model.Exercise;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    @Query("SELECT e FROM Exercise e WHERE e.category.wc_id = :wcId")
    List<Exercise> findByCategoryId(@Param("wcId") Integer wcId);

}

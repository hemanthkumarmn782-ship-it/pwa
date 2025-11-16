package com.fitnessapp.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.fitnessapp.fitnessapp.model.Accuracy;
import java.util.List;

public interface AccuracyRepository extends JpaRepository<Accuracy, Integer> {

    @Query(value = "SELECT * FROM accuracy WHERE user_id = ?1 AND exercise_id = ?2 ORDER BY date DESC LIMIT 5", nativeQuery = true)
    List<Accuracy> findLast5ByUserAndExercise(Integer userId, Integer exerciseId);

}

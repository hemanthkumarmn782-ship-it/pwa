package com.fitnessapp.fitnessapp.controller;

import com.fitnessapp.fitnessapp.dto.AccuracyRequest;
import com.fitnessapp.fitnessapp.model.Accuracy;
import com.fitnessapp.fitnessapp.model.User;
import com.fitnessapp.fitnessapp.model.Exercise;
import com.fitnessapp.fitnessapp.repository.AccuracyRepository;
import com.fitnessapp.fitnessapp.repository.UserRepository;
import com.fitnessapp.fitnessapp.repository.ExerciseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/accuracy")
@CrossOrigin(origins = "https://pwa-s2rd.onrender.com")

public class AccuracyController {

    @Autowired
    private AccuracyRepository accuracyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    // Save accuracy score
    @PostMapping("/save")
public Accuracy saveAccuracy(@RequestBody AccuracyRequest req) {

    User user = userRepository.findById(req.user_id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Exercise exercise = exerciseRepository.findById(req.exercise_id)
            .orElseThrow(() -> new RuntimeException("Exercise not found"));

    Accuracy acc = new Accuracy();
    acc.setUser(user);
    acc.setExercise(exercise);
    acc.setPosture_accuracy(req.posture_accuracy);
    acc.setDate(LocalDateTime.now());

    return accuracyRepository.save(acc);
}

    // Last 5 accuracy scores
    @GetMapping("/recent/{userId}/{exerciseId}")
     public List<Accuracy> getRecentAccuracy(
        @PathVariable Integer userId,
        @PathVariable Integer exerciseId
 ) {
         return accuracyRepository.findLast5ByUserAndExercise(userId, exerciseId);
    }
}

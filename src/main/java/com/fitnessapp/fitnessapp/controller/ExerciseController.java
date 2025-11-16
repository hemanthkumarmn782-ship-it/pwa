package com.fitnessapp.fitnessapp.controller;

import com.fitnessapp.fitnessapp.model.Exercise;
import com.fitnessapp.fitnessapp.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = "*")
public class ExerciseController {

    @Autowired
    private ExerciseRepository repo;

    @GetMapping("/all")
    public List<Exercise> getAllExercises() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Exercise> getExerciseById(@PathVariable int id) {
        return repo.findById(id);
    }

    @PostMapping("/add")
    public Exercise addExercise(@RequestBody Exercise exercise) {
        Objects.requireNonNull(exercise, "exercise must not be null");
        return repo.save(exercise);
    }

    @PutMapping("/update/{id}")
    public Exercise updateExercise(@PathVariable int id, @RequestBody Exercise updated) {
        return repo.findById(id)
                .map(ex -> {
                    ex.setExercise_name(updated.getExercise_name());
                    ex.setGif_path(updated.getGif_path());
                    ex.setIs_posture_based(updated.getIs_posture_based());
                    ex.setCategory(updated.getCategory());
                    return repo.save(ex);
                }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteExercise(@PathVariable int id) {
        repo.deleteById(id);
    }
}

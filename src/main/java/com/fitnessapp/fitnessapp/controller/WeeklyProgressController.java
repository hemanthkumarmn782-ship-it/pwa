package com.fitnessapp.fitnessapp.controller;

import com.fitnessapp.fitnessapp.model.WeeklyProgress;
import com.fitnessapp.fitnessapp.repository.WeeklyProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/weekly-progress")
@CrossOrigin(origins = "*")
public class WeeklyProgressController {

    @Autowired
    private WeeklyProgressRepository repo;

    @GetMapping("/all")
    public List<WeeklyProgress> getAllProgress() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<WeeklyProgress> getById(@PathVariable int id) {
        return repo.findById(id);
    }

    @PostMapping("/add")
    public WeeklyProgress add(@RequestBody WeeklyProgress progress) {
        Objects.requireNonNull(progress, "progress must not be null");
        return repo.save(progress);
    }

    @PutMapping("/update/{id}")
    public WeeklyProgress update(@PathVariable int id, @RequestBody WeeklyProgress updated) {
        return repo.findById(id)
                .map(p -> {
                    p.setWeek_start(updated.getWeek_start());
                    p.setTotal_workouts(updated.getTotal_workouts());
                    p.setAvg_score(updated.getAvg_score());
                    p.setNext_target_reps(updated.getNext_target_reps());
                    p.setUser(updated.getUser());
                    return repo.save(p);
                }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repo.deleteById(id);
    }
}

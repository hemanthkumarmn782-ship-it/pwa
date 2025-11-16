package com.fitnessapp.fitnessapp.controller;

import com.fitnessapp.fitnessapp.model.WorkoutPlan;
import com.fitnessapp.fitnessapp.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/workout-plans")
@CrossOrigin(origins = "*")
public class WorkoutPlanController {

    @Autowired
    private WorkoutPlanRepository repo;

    @GetMapping("/all")
    public List<WorkoutPlan> getAllPlans() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<WorkoutPlan> getPlanById(@PathVariable long id) {
        // convert int -> Long before calling repository
        return repo.findById(id);
    }

    @PostMapping("/add")
    public WorkoutPlan addPlan(@RequestBody WorkoutPlan plan) {
        Objects.requireNonNull(plan, "plan must not be null");
        return repo.save(plan);
    }

    @PutMapping("/update/{id}")
    public WorkoutPlan updatePlan(@PathVariable long id, @RequestBody WorkoutPlan updated) {
        // convert int -> Long for repository usage
        return repo.findById(id)
                .map(p -> {
                    p.setPlanName(updated.getPlanName());
                    p.setLevel(updated.getLevel());
                    p.setTotalDuration(updated.getTotalDuration());
                    return repo.save(p);
                }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deletePlan(@PathVariable long id) {
        // convert int -> Long for deleteById
        repo.deleteById(id);
    }
}

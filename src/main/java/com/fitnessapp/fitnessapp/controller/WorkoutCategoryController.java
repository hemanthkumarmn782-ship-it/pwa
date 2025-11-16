package com.fitnessapp.fitnessapp.controller;

import com.fitnessapp.fitnessapp.model.WorkoutCategory;
import com.fitnessapp.fitnessapp.repository.WorkoutCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/workout-categories")
@CrossOrigin(origins = "*")
public class WorkoutCategoryController {

    @Autowired
    private WorkoutCategoryRepository repo;

    @GetMapping("/all")
    public List<WorkoutCategory> getAllCategories() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<WorkoutCategory> getCategoryById(@PathVariable int id) {
        return repo.findById(id);
    }

    @PostMapping("/add")
    public WorkoutCategory addCategory(@RequestBody WorkoutCategory category) {
        Objects.requireNonNull(category, "category must not be null");
        return repo.save(category);
    }

    @PutMapping("/update/{id}")
    public WorkoutCategory updateCategory(@PathVariable int id, @RequestBody WorkoutCategory updated) {
        return repo.findById(id)
                .map(cat -> {
                    cat.setCategory_name(updated.getCategory_name());
                    return repo.save(cat);
                }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id) {
        repo.deleteById(id);
    }
}

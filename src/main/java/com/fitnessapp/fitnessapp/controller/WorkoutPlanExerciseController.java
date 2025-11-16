package com.fitnessapp.fitnessapp.controller;

import com.fitnessapp.fitnessapp.model.WorkoutPlanExercise;
import com.fitnessapp.fitnessapp.model.Exercise;
import com.fitnessapp.fitnessapp.repository.WorkoutPlanExerciseRepository;
import com.fitnessapp.fitnessapp.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/workout-plan-exercises")
@CrossOrigin(origins = "*")
public class WorkoutPlanExerciseController {

    @Autowired
    private WorkoutPlanExerciseRepository repo;

    @Autowired
    private ExerciseRepository exerciseRepo;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        List<WorkoutPlanExercise> list = repo.findAll();
        Map<String, Object> res = new HashMap<>();
        res.put("items", list);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        return repo.findById(id)
                .map(e -> {
                    Map<String, Object> res = new HashMap<>();
                    res.put("item", e);
                    return ResponseEntity.ok(res);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody WorkoutPlanExercise exercise) {

        // Ensure exercise exists
        if (exercise.getExercise() == null ||
            exercise.getExercise().getExercise_id() == null) {

            Map<String, Object> err = new HashMap<>();
            err.put("error", "Exercise must contain valid exercise_id");
            return ResponseEntity.badRequest().body(err);
        }

        Exercise ex = exerciseRepo.findById(
                exercise.getExercise().getExercise_id()
        ).orElse(null);

        if (ex == null) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "Exercise not found");
            return ResponseEntity.badRequest().body(err);
        }

        exercise.setExercise(ex);

        WorkoutPlanExercise saved = repo.save(exercise);
        Map<String, Object> res = new HashMap<>();
        res.put("created", saved);

        return ResponseEntity.ok(res);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody WorkoutPlanExercise updated) {

        return repo.findById(id)
                .map(e -> {

                    e.setPlanId(updated.getPlanId());
                    e.setDayNo(updated.getDayNo());
                    e.setOrderNo(updated.getOrderNo());
                    e.setReps(updated.getReps());
                    e.setSets(updated.getSets());

                    // Update exercise relation
                    if (updated.getExercise() != null &&
                        updated.getExercise().getExercise_id() != null) {

                        Exercise exObj =
                                exerciseRepo.findById(updated.getExercise().getExercise_id())
                                        .orElse(null);

                        if (exObj != null) {
                            e.setExercise(exObj);
                        }
                    }

                    WorkoutPlanExercise saved = repo.save(e);
                    Map<String, Object> res = new HashMap<>();
                    res.put("updated", (Object) saved);

                    return ResponseEntity.ok(res);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {

        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);

        Map<String, Object> res = new HashMap<>();
        res.put("deleted", (Object) id);

        return ResponseEntity.ok(res);
    }
}

package com.fitnessapp.fitnessapp.model;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exercise_id;

    @ManyToOne
    @JoinColumn(name = "wc_id")
    private WorkoutCategory category;

    private String exercise_name;


    private String gif_path;
    private Boolean is_posture_based;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private List<WorkoutPlanExercise> workoutPlanExercises;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Accuracy> accuracyRecords;

    // Getters and Setters
    public Integer getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(Integer exercise_id) {
        this.exercise_id = exercise_id;
    }

    public WorkoutCategory getCategory() {
        return category;
    }

    public void setCategory(WorkoutCategory category) {
        this.category = category;
    }

    public String getExercise_name() {
        return exercise_name;
    }

    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }

    public String getGif_path() {
        return gif_path;
    }

    public void setGif_path(String gif_path) {
        this.gif_path = gif_path;
    }

    public Boolean getIs_posture_based() {
        return is_posture_based;
    }

    public void setIs_posture_based(Boolean is_posture_based) {
        this.is_posture_based = is_posture_based;
    }

    public List<WorkoutPlanExercise> getWorkoutPlanExercises() {
        return workoutPlanExercises;
    }

    public void setWorkoutPlanExercises(List<WorkoutPlanExercise> workoutPlanExercises) {
        this.workoutPlanExercises = workoutPlanExercises;
    }

    public List<Accuracy> getAccuracyRecords() {
        return accuracyRecords;
    }

    public void setAccuracyRecords(List<Accuracy> accuracyRecords) {
        this.accuracyRecords = accuracyRecords;
    }
}

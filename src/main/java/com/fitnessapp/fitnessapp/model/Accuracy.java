package com.fitnessapp.fitnessapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "accuracy")
public class Accuracy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accuracy_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({
            "accuracyRecords",
            "progressRecords"
    })
    private User user;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    @JsonIgnoreProperties({
            "accuracyRecords",
            "workoutPlanExercises"
    })
    private Exercise exercise;

    private LocalDateTime date;
    private Float posture_accuracy;

    // Getters / Setters
    public Integer getAccuracy_id() { return accuracy_id; }
    public void setAccuracy_id(Integer accuracy_id) { this.accuracy_id = accuracy_id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Exercise getExercise() { return exercise; }
    public void setExercise(Exercise exercise) { this.exercise = exercise; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public Float getPosture_accuracy() { return posture_accuracy; }
    public void setPosture_accuracy(Float posture_accuracy) { this.posture_accuracy = posture_accuracy; }
}

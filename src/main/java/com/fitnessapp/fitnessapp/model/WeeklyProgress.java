package com.fitnessapp.fitnessapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "weekly_progress")
public class WeeklyProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer progress_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate week_start;
    private Integer total_workouts;
    private Float avg_score;
    private Integer next_target_reps;

    // Getters and Setters
    public Integer getProgress_id() {
        return progress_id;
    }

    public void setProgress_id(Integer progress_id) {
        this.progress_id = progress_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getWeek_start() {
        return week_start;
    }

    public void setWeek_start(LocalDate week_start) {
        this.week_start = week_start;
    }

    public Integer getTotal_workouts() {
        return total_workouts;
    }

    public void setTotal_workouts(Integer total_workouts) {
        this.total_workouts = total_workouts;
    }

    public Float getAvg_score() {
        return avg_score;
    }

    public void setAvg_score(Float avg_score) {
        this.avg_score = avg_score;
    }

    public Integer getNext_target_reps() {
        return next_target_reps;
    }

    public void setNext_target_reps(Integer next_target_reps) {
        this.next_target_reps = next_target_reps;
    }
}

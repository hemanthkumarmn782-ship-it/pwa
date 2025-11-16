package com.fitnessapp.fitnessapp.dto;

import java.time.LocalDate;

public class ProgressReportRequest {

    private Long userId;
    private LocalDate weekStart;      // e.g., 2024-01-15
    private Integer totalWorkouts;    // how many workouts completed
    private Float avgScore;           // score 0–100

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(LocalDate weekStart) {
        this.weekStart = weekStart;
    }

    public Integer getTotalWorkouts() {
        return totalWorkouts;
    }

    public void setTotalWorkouts(Integer totalWorkouts) {
        this.totalWorkouts = totalWorkouts;
    }

    public Float getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Float avgScore) {
        this.avgScore = avgScore;
    }
}

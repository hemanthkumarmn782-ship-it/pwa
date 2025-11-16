package com.fitnessapp.fitnessapp.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "workout_plan_exercises")
public class WorkoutPlanExercise implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planExId;

    @Column(nullable = false)
    private Long planId;

    // 🔥 FIX: Add proper Exercise relationship
    @ManyToOne
    @JoinColumn(name = "exercise_id", referencedColumnName = "exercise_id", nullable = false)
    private Exercise exercise;   // <--- THIS is what mappedBy="exercise" expects

    // day number in plan (1..7 or 1..30)
    @Column(nullable = false)
    private Integer dayNo;

    @Column(nullable = false)
    private Integer orderNo;

    private Integer reps;
    private Integer sets;

    // -------------------------------------
    // GETTERS / SETTERS
    // -------------------------------------

    public Long getPlanExId() { return planExId; }
    public void setPlanExId(Long planExId) { this.planExId = planExId; }

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }

    public Exercise getExercise() { return exercise; }
    public void setExercise(Exercise exercise) { this.exercise = exercise; }

    public Integer getDayNo() { return dayNo; }
    public void setDayNo(Integer dayNo) { this.dayNo = dayNo; }

    public Integer getOrderNo() { return orderNo; }
    public void setOrderNo(Integer orderNo) { this.orderNo = orderNo; }

    public Integer getReps() { return reps; }
    public void setReps(Integer reps) { this.reps = reps; }

    public Integer getSets() { return sets; }
    public void setSets(Integer sets) { this.sets = sets; }
}

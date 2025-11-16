package com.fitnessapp.fitnessapp.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "workout_plan")
public class WorkoutPlan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @Column(nullable = false)
    private String planName;

    // level uses your schema values: "Arms & Chest", "Abs", "Legs", "Full Body"
    @Column(nullable = false)
    private String level;

    // minutes approx
    private Integer totalDuration;

    // optional: who generated it (null if generic)
    private Long createdByUserId;

    // plan type: "weekly" or "monthly"
    private String planType;

    // getters / setters
    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public Integer getTotalDuration() { return totalDuration; }
    public void setTotalDuration(Integer totalDuration) { this.totalDuration = totalDuration; }
    public Long getCreatedByUserId() { return createdByUserId; }
    public void setCreatedByUserId(Long createdByUserId) { this.createdByUserId = createdByUserId; }
    public String getPlanType() { return planType; }
    public void setPlanType(String planType) { this.planType = planType; }
}

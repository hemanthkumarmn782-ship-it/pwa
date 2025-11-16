package com.fitnessapp.fitnessapp.dto;

import java.util.List;

public class PlanDetailDTO {
    private Long planId;
    private String planName;
    private String level;
    private String planType;
    private Integer totalDuration;
    private List<DayDTO> days;

    public static class DayDTO {
        public Integer dayNo;
        public List<ExerciseInPlan> exercises;
    }

    public static class ExerciseInPlan {
        public Long exerciseId;
        public String exerciseName;
        public String gifPath;
        public Integer orderNo;
        public Integer reps;
        public Integer sets;
        public String predictedType;
    }

    // getters + setters
    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getPlanType() { return planType; }
    public void setPlanType(String planType) { this.planType = planType; }
    public Integer getTotalDuration() { return totalDuration; }
    public void setTotalDuration(Integer totalDuration) { this.totalDuration = totalDuration; }
    public List<DayDTO> getDays() { return days; }
    public void setDays(List<DayDTO> days) { this.days = days; }
}

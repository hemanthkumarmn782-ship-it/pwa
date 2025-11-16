package com.fitnessapp.fitnessapp.dto;

public class PlanResponse {
    private Long planId;
    private String message;

    public PlanResponse(Long planId, String message) {
        this.planId = planId;
        this.message = message;
    }

    public Long getPlanId() { return planId; }
    public String getMessage() { return message; }
}

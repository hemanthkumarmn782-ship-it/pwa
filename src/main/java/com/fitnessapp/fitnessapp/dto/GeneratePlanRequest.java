package com.fitnessapp.fitnessapp.dto;

public class GeneratePlanRequest {
    private Long userId;
    private String category;  // "Arms & Chest", "Legs", etc.
    private String type;      // "weekly" or "monthly"

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}

package com.fitnessapp.fitnessapp.controller;

import com.fitnessapp.fitnessapp.dto.GeneratePlanRequest;
import com.fitnessapp.fitnessapp.dto.PlanDetailDTO;
import com.fitnessapp.fitnessapp.dto.PlanResponse;
import com.fitnessapp.fitnessapp.dto.ProgressReportRequest;
import com.fitnessapp.fitnessapp.model.WorkoutPlan;
import com.fitnessapp.fitnessapp.service.IntermediateWorkoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Endpoints for Intermediate plan generation and fetching.
 */
@RestController
@RequestMapping("/api/intermediate")
@CrossOrigin(origins = "https://pwa-s2rd.onrender.com")
public class IntermediateWorkoutController {

    private final IntermediateWorkoutService service;

    public IntermediateWorkoutController(IntermediateWorkoutService service) {
        this.service = service;
    }

    /**
     * POST /api/intermediate/generate-plan
     * Body: { userId, category, type }  type = "weekly" | "monthly"
     */
    @PostMapping("/generate-plan")
    public ResponseEntity<?> generatePlan(@RequestBody GeneratePlanRequest req) {
        try {
            PlanResponse resp = service.generatePlan(req);
            Map<String, Object> body = new HashMap<>();
            body.put("planId", (Object) resp.getPlanId());
            body.put("message", resp.getMessage());
            return ResponseEntity.ok(body);
        } catch (IllegalStateException ex) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", ex.getMessage());
            return ResponseEntity.badRequest().body(err);
        } catch (Exception ex) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "server_error");
            return ResponseEntity.status(500).body(err);
        }
    }

    /**
     * GET /api/intermediate/plan/{planId}
     */
    @GetMapping("/plan/{planId}")
    public ResponseEntity<?> getPlan(@PathVariable Long planId) {
        PlanDetailDTO dto = service.getPlanDetail(planId);
        if (dto == null) return ResponseEntity.notFound().build();
        Map<String, Object> res = new HashMap<>();
        res.put("plan", dto);
        return ResponseEntity.ok(res);
    }

    /**
     * GET /api/intermediate/user-plans/{userId}
     */
    @GetMapping("/user-plans/{userId}")
    public ResponseEntity<?> getUserPlans(@PathVariable Long userId) {
        List<WorkoutPlan> list = service.listPlansByUser(userId);
        Map<String, Object> res = new HashMap<>();
        res.put("plans", list);
        return ResponseEntity.ok(res);
    }

    /**
     * POST /api/intermediate/report-progress
     * Body: ProgressReportRequest (userId, weekStart, totalWorkouts, avgScore)
     */
    @PostMapping("/report-progress")
    public ResponseEntity<?> reportProgress(@RequestBody ProgressReportRequest req) {
        service.recordWeeklyProgress(req);
        Map<String, Object> res = new HashMap<>();
        res.put("status", "ok");
        return ResponseEntity.ok(res);
    }
}

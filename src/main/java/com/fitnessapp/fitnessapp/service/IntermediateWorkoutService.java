package com.fitnessapp.fitnessapp.service;

import com.fitnessapp.fitnessapp.dto.GeneratePlanRequest;
import com.fitnessapp.fitnessapp.dto.PlanDetailDTO;
import com.fitnessapp.fitnessapp.dto.PlanResponse;
import com.fitnessapp.fitnessapp.dto.ProgressReportRequest;
import com.fitnessapp.fitnessapp.model.Exercise;
import com.fitnessapp.fitnessapp.model.WeeklyProgress;
import com.fitnessapp.fitnessapp.model.WorkoutPlan;
import com.fitnessapp.fitnessapp.model.WorkoutPlanExercise;
import com.fitnessapp.fitnessapp.repository.ExerciseRepository;
import com.fitnessapp.fitnessapp.repository.WeeklyProgressRepository;
import com.fitnessapp.fitnessapp.repository.WorkoutPlanExerciseRepository;
import com.fitnessapp.fitnessapp.repository.WorkoutPlanRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class IntermediateWorkoutService {

    private final ExerciseRepository exerciseRepo;
    private final WorkoutPlanRepository planRepo;
    private final WorkoutPlanExerciseRepository planExRepo;
    private final WeeklyProgressRepository progressRepo;
   // private final Random rnd = new Random();

    public IntermediateWorkoutService(
            ExerciseRepository exerciseRepo,
            WorkoutPlanRepository planRepo,
            WorkoutPlanExerciseRepository planExRepo,
            WeeklyProgressRepository progressRepo) {

        this.exerciseRepo = exerciseRepo;
        this.planRepo = planRepo;
        this.planExRepo = planExRepo;
        this.progressRepo = progressRepo;
    }

    // --------------------------------------------------------
    //  Generate Weekly or Monthly Plan
    // --------------------------------------------------------
    @Transactional
    public PlanResponse generatePlan(GeneratePlanRequest req) {

        String category = req.getCategory();
        String type = req.getType().toLowerCase();
        Long userId = req.getUserId();

        int days = type.equals("monthly") ? 30 : 7;
        int exercisesPerDay = computeBaselineExercisesPerDay(userId);

        Integer wcId = mapCategoryToWcId(category);
        List<Exercise> pool = exerciseRepo.findByCategoryId(wcId);

        if (pool == null || pool.isEmpty()) {
            throw new IllegalStateException("No exercises found for category: " + category);
        }

        Map<String, List<Exercise>> buckets = classifyExercises(pool);

        WorkoutPlan plan = new WorkoutPlan();
        plan.setPlanName(category + " " + capitalize(type) + " Plan");
        plan.setLevel(category);
        plan.setPlanType(type);
        plan.setCreatedByUserId(userId);
        plan.setTotalDuration(days * 30);
        plan = planRepo.save(plan);

        for (int day = 1; day <= days; day++) {
            List<WorkoutPlanExercise> today = buildDay(plan, day, exercisesPerDay, buckets);
            planExRepo.saveAll(today);
        }

        return new PlanResponse(plan.getPlanId(), "Plan generated");
    }

    // --------------------------------------------------------
    //   Build a single day
    // --------------------------------------------------------
    private List<WorkoutPlanExercise> buildDay(
        WorkoutPlan plan,
        int dayNo,
        int count,
        Map<String, List<Exercise>> buckets) {

    List<WorkoutPlanExercise> all = new ArrayList<>();

    int warm = 2;
    int stretch = 1;
    int strength = count - warm - stretch;

    // Pick exercises
    List<Exercise> warmups = pick(buckets.get("warmup"), warm);
    List<Exercise> strengths = pick(buckets.get("strength"), strength);
    List<Exercise> stretches = pick(buckets.get("stretch"), stretch);

    // Convert ALL exercises → split sets → add individually
    for (Exercise e : warmups)
        all.addAll(makePlanEx(plan, dayNo, 0, e, "warmup"));

    for (Exercise e : strengths)
        all.addAll(makePlanEx(plan, dayNo, 0, e, "strength"));

    for (Exercise e : stretches)
        all.addAll(makePlanEx(plan, dayNo, 0, e, "stretch"));

    // RANDOMIZE completely
    Collections.shuffle(all);

    // Reassign order numbers AFTER shuffle
    int order = 1;
    for (WorkoutPlanExercise ex : all)
        ex.setOrderNo(order++);

    return all;
}


    // --------------------------------------------------------
    // Create WorkoutPlanExercise row
    // --------------------------------------------------------
    private List<WorkoutPlanExercise> makePlanEx(
        WorkoutPlan plan,
        int day,
        int orderNo,
        Exercise exercise,
        String type) {

    List<WorkoutPlanExercise> multiple = new ArrayList<>();

    int reps;
    int sets;

    switch (type) {
        case "warmup":
            reps = 20;
            sets = 1;
            break;

        case "stretch":
            reps = 30;
            sets = 1;
            break;

        default:  // strength
            reps = 12;
            sets = 3;
            break;
    }

    // Create one row PER SET (this is your requirement)
    for (int s = 1; s <= sets; s++) {
        WorkoutPlanExercise ex = new WorkoutPlanExercise();
        ex.setPlanId(plan.getPlanId());
        ex.setDayNo(day);
        ex.setOrderNo(orderNo++);   // set 1 → 1, set 2 → 2, set 3 → 3
        ex.setExercise(exercise);
        ex.setReps(reps);
        ex.setSets(1);  // IMPORTANT: always 1 set per entry

        multiple.add(ex);
    }

    return multiple;
}


    // --------------------------------------------------------
    //   Classification Helper
    // --------------------------------------------------------
    private Map<String, List<Exercise>> classifyExercises(List<Exercise> pool) {

        Map<String, List<Exercise>> buckets = new HashMap<>();
        buckets.put("warmup", new ArrayList<>());
        buckets.put("strength", new ArrayList<>());
        buckets.put("stretch", new ArrayList<>());

        for (Exercise e : pool) {
            String name = e.getExercise_name().toLowerCase();
            buckets.get(predictType(name)).add(e);
        }

        if (buckets.get("warmup").isEmpty())
            buckets.get("warmup").addAll(pool.subList(0, Math.min(pool.size(), 3)));

        if (buckets.get("stretch").isEmpty())
            buckets.get("stretch").addAll(pool.subList(0, Math.min(pool.size(), 3)));

        if (buckets.get("strength").isEmpty())
            buckets.get("strength").addAll(pool);

        return buckets;
    }

    private String predictType(String name) {
        name = name.toLowerCase();

        String[] warm = { "jump", "jog", "warm", "knee", "march", "circle", "skip" };
        for (String w : warm) if (name.contains(w)) return "warmup";

        String[] stretch = { "stretch", "cobra", "hold", "hamstring", "quad" };
        for (String s : stretch) if (name.contains(s)) return "stretch";

        return "strength";
    }

    private List<Exercise> pick(List<Exercise> src, int n) {
        if (src.isEmpty()) return src;
        List<Exercise> copy = new ArrayList<>(src);
        Collections.shuffle(copy);
        List<Exercise> result = new ArrayList<>();
        for (int i = 0; i < n; i++)
            result.add(copy.get(i % copy.size()));
        return result;
    }

    // --------------------------------------------------------
    //  Compute exercises/day based on progress
    // --------------------------------------------------------
    private int computeBaselineExercisesPerDay(Long uid) {
        if (uid == null) return 7;

        List<WeeklyProgress> list = progressRepo.findByUserId(uid.intValue());
        if (list.isEmpty()) return 7;

        WeeklyProgress recent =
                list.stream().max(Comparator.comparing(WeeklyProgress::getWeek_start)).orElse(null);

        if (recent == null || recent.getAvg_score() == null) return 7;

        float score = recent.getAvg_score();
        int base = 7;

        if (score >= 85) base++;
        if (score >= 92) base++;

        return Math.min(base, 12);
    }

    // --------------------------------------------------------
    //   Fetch Plan
    // --------------------------------------------------------
    @Transactional(readOnly = true)
    public PlanDetailDTO getPlanDetail(Long planId) {

        WorkoutPlan plan = planRepo.findById(planId).orElse(null);
        if (plan == null) return null;

        List<WorkoutPlanExercise> list =
                planExRepo.findByPlanIdOrderByDayNoAscOrderNoAsc(planId);

        Map<Integer, PlanDetailDTO.DayDTO> days = new LinkedHashMap<>();

        for (WorkoutPlanExercise p : list) {

            PlanDetailDTO.DayDTO day =
                    days.computeIfAbsent(p.getDayNo(), d -> {
                        PlanDetailDTO.DayDTO dd = new PlanDetailDTO.DayDTO();
                        dd.dayNo = d;
                        dd.exercises = new ArrayList<>();
                        return dd;
                    });

            Exercise e = p.getExercise();

            PlanDetailDTO.ExerciseInPlan ep = new PlanDetailDTO.ExerciseInPlan();
            ep.exerciseId = e.getExercise_id().longValue();
            ep.exerciseName = e.getExercise_name();
            ep.gifPath = e.getGif_path();
            ep.orderNo = p.getOrderNo();
            ep.reps = p.getReps();
            ep.sets = p.getSets();
            ep.predictedType = predictType(ep.exerciseName);

            day.exercises.add(ep);
        }

        PlanDetailDTO dto = new PlanDetailDTO();
        dto.setPlanId(plan.getPlanId());
        dto.setPlanName(plan.getPlanName());
        dto.setLevel(plan.getLevel());
        dto.setPlanType(plan.getPlanType());
        dto.setTotalDuration(plan.getTotalDuration());
        dto.setDays(new ArrayList<>(days.values()));

        return dto;
    }

    // --------------------------------------------------------
    //   Helper Methods Missing Earlier
    // --------------------------------------------------------
    private Integer mapCategoryToWcId(String category) {
        if (category == null) return null;
        String c = category.toLowerCase();

        if (c.contains("arm") || c.contains("chest")) return 1;
        if (c.contains("abs") || c.contains("core")) return 2;
        if (c.contains("leg")) return 3;
        if (c.contains("full")) return 4;

        return null;
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    @Transactional(readOnly = true)
    public List<WorkoutPlan> listPlansByUser(Long userId) {
        return planRepo.findByCreatedByUserId(userId);
    }

    @Transactional
    public void recordWeeklyProgress(ProgressReportRequest req) {

        if (req == null || req.getUserId() == null || req.getWeekStart() == null)
            return;

        Integer userId = req.getUserId().intValue();
        List<WeeklyProgress> list = progressRepo.findByUserId(userId);

        WeeklyProgress existing = list.stream()
                .filter(w -> w.getWeek_start().equals(req.getWeekStart()))
                .findFirst()
                .orElse(null);

        WeeklyProgress p = existing == null ? new WeeklyProgress() : existing;

        p.setWeek_start(req.getWeekStart());
        p.setTotal_workouts(req.getTotalWorkouts());
        p.setAvg_score(req.getAvgScore());

        int currentTarget = p.getNext_target_reps() == null ? 12 : p.getNext_target_reps();
        p.setNext_target_reps(computeNextTargetReps(req.getAvgScore(), currentTarget));

        progressRepo.save(p);
    }

    private int computeNextTargetReps(Float avgScore, int currentTarget) {
        if (avgScore == null) return currentTarget;

        if (avgScore >= 92) return Math.min(20, currentTarget + 2);
        if (avgScore >= 85) return Math.min(18, currentTarget + 1);
        if (avgScore < 50) return Math.max(8, currentTarget - 1);

        return currentTarget;
    }
}

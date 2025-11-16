package com.fitnessapp.fitnessapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    private String full_name;

    @Column(unique = true)
    private String email;

    private String password;
    private Integer age;
    private String gender;
    private Integer height_cm;
    private Integer weight_kg;
    private LocalDateTime join_date;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Accuracy> accuracyRecords;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<WeeklyProgress> progressRecords;

    // Getters and Setters
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getHeight_cm() {
        return height_cm;
    }

    public void setHeight_cm(Integer height_cm) {
        this.height_cm = height_cm;
    }

    public Integer getWeight_kg() {
        return weight_kg;
    }

    public void setWeight_kg(Integer weight_kg) {
        this.weight_kg = weight_kg;
    }

    public LocalDateTime getJoin_date() {
        return join_date;
    }

    public void setJoin_date(LocalDateTime join_date) {
        this.join_date = join_date;
    }

    public List<Accuracy> getAccuracyRecords() {
        return accuracyRecords;
    }

    public void setAccuracyRecords(List<Accuracy> accuracyRecords) {
        this.accuracyRecords = accuracyRecords;
    }

    public List<WeeklyProgress> getProgressRecords() {
        return progressRecords;
    }

    public void setProgressRecords(List<WeeklyProgress> progressRecords) {
        this.progressRecords = progressRecords;
    }
}

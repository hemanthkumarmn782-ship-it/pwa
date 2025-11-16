package com.fitnessapp.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fitnessapp.fitnessapp.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}

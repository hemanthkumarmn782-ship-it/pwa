package com.fitnessapp.fitnessapp.controller;

import com.fitnessapp.fitnessapp.model.User;
import com.fitnessapp.fitnessapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "https://pwa-s2rd.onrender.com")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return userRepository.findById(id);
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        user.setJoin_date(java.time.LocalDateTime.now());
        return userRepository.save(user);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFull_name(updatedUser.getFull_name());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                    user.setAge(updatedUser.getAge());
                    user.setGender(updatedUser.getGender());
                    user.setHeight_cm(updatedUser.getHeight_cm());
                    user.setWeight_kg(updatedUser.getWeight_kg());
                    return userRepository.save(user);
                }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }
}

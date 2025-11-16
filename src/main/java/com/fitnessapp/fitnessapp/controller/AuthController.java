package com.fitnessapp.fitnessapp.controller;

import com.fitnessapp.fitnessapp.model.User;
import com.fitnessapp.fitnessapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")  
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ✅ LOGIN ENDPOINT (JSON Response)
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User loginData) {

        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findByEmail(loginData.getEmail());

        if (user == null) {
            response.put("status", "error");
            response.put("message", "User not found");
            return response;
        }

        if (!user.getPassword().equals(loginData.getPassword())) {
            response.put("status", "error");
            response.put("message", "Invalid password");
            return response;
        }

        // SUCCESS RESPONSE (Frontend will store user_id + name)
        response.put("status", "success");
        response.put("user_id", user.getUser_id());
        response.put("full_name", user.getFull_name());
        response.put("email", user.getEmail());

        return response;
    }
}

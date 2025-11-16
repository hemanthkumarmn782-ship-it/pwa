package com.fitnessapp.fitnessapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FitnessappApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessappApplication.class, args);
	    System.out.println("✅ Fitness App Backend Running on http://localhost:8080");
	}

}

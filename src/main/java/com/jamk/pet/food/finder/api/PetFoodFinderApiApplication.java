package com.jamk.pet.food.finder.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class PetFoodFinderApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetFoodFinderApiApplication.class, args);
	}

}

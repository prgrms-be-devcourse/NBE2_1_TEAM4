package com.example.gc_coffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GcCoffeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GcCoffeeApplication.class, args);
	}

}

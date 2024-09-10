package edu.example.coffeeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CoffeeProJectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeProJectApplication.class, args);
    }

}

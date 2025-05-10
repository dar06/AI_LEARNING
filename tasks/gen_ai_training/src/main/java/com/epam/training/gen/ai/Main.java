package com.epam.training.gen.ai;

import com.epam.training.gen.ai.service.PromptSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    @Autowired
    private PromptSettingService promptSettingService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            displayWithLowerTemperature();
            displayWithHigherTemperature();
        };
    }

    private void displayWithLowerTemperature() {
        String describeAlienPlanet = promptSettingService.displayMessage("Describe alien planet" , 0.1);
        System.out.println("Lower Temperature Set" + describeAlienPlanet);
    }

    private void displayWithHigherTemperature() {
        String describeAlienPlanet = promptSettingService.displayMessage("Describe alien planet", 1.0);
        System.out.println("Higher Temperature Set " + describeAlienPlanet);
    }


}




package com.epam.training.gen.ai;

import com.microsoft.semantickernel.services.ServiceNotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenAiTrainingApplication {
    public static void main(String[] args) throws ServiceNotFoundException {
        SpringApplication.run(GenAiTrainingApplication.class, args);

    }

}

package com.careerpilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.careerpilot.config.GeminiProperties;
import com.careerpilot.config.GroqProperties;

@SpringBootApplication
@EnableConfigurationProperties({GeminiProperties.class, GroqProperties.class})
public class CareerPilotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareerPilotApplication.class, args);
    }
}

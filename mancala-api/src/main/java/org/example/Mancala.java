package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@ConfigurationPropertiesScan("org.example.infrastructure.properties")
public class Mancala {
    public static void main(String[] args) {
        SpringApplication.run(Mancala.class, args);
    }
}

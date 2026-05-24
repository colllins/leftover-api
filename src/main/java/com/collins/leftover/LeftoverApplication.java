package com.collins.leftover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LeftoverApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeftoverApplication.class, args);
    }
}

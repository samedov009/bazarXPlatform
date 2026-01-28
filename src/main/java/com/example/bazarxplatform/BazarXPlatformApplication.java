package com.example.bazarxplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BazarXPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(BazarXPlatformApplication.class, args);
    }
}

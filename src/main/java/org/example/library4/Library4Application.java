package org.example.library4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Library4Application {
    public static void main(String[] args) {
        SpringApplication.run(Library4Application.class, args);
    }
}
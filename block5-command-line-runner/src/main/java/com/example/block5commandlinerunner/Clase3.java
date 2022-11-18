package com.example.block5commandlinerunner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Clase3 implements CommandLineRunner {
    public void run(String... args) {
        System.out.println("Soy la tercera clase");
        for (String arg : args) {
            System.out.println("\t- " + arg);
        }
    }
}

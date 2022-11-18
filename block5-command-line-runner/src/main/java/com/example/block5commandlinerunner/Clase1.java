package com.example.block5commandlinerunner;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class Clase1 {
    @PostConstruct
    private void inicial() {
        System.out.println("Hola desde clase inicial");
    }
}

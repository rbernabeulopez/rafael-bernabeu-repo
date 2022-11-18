package com.example.block5commandlinerunner;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Clase1 {
    @PostConstruct
    private void inicial() {
        System.out.println("Hola desde clase inicial");
    }
}

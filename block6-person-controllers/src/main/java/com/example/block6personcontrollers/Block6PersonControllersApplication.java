package com.example.block6personcontrollers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Block6PersonControllersApplication {

    public static void main(String[] args) {
        SpringApplication.run(Block6PersonControllersApplication.class, args);
    }

    @Bean
    List<Ciudad> generaCiudades() {
        return new ArrayList<>();
    }
}

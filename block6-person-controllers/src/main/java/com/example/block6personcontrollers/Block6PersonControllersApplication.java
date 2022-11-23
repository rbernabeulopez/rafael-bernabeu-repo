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

    @Bean("bean1")
    Persona persona1() {
        Persona persona = new Persona();
        persona.setNombre("Chuchi");
        persona.setCiudad("Madrid");
        persona.setEdad("22");
        return persona;
    }

    @Bean("bean2")
    Persona persona2() {
        Persona persona = new Persona();
        persona.setNombre("Rafa");
        persona.setCiudad("Alicante");
        persona.setEdad("43");
        return persona;
    }

    @Bean("bean3")
    Persona persona3() {
        Persona persona = new Persona();
        persona.setNombre("Pablo");
        persona.setCiudad("Tarragona");
        persona.setEdad("37");
        return persona;
    }
}

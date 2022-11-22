package com.example.block5properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Block5PropertiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(Block5PropertiesApplication.class, args);
    }

    @Value("${greeting}")
    private String greeting;

    @Value("${my.number}")
    private String myNumber;

    @Value("${new.value:no te funciona}")
    private String newProperty;

    @Bean
    public void imprimePropiedades() {
        System.out.println("El valor de greeting es: (valor de ‘" + greeting + "’)");
        System.out.println("El valor de my.number es: (valor de ‘" +myNumber + "’)");
        System.out.println("El valor de newProperty es: (valor de ‘" +newProperty + "’)");
    }

}

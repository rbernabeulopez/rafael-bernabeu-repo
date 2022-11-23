package com.example.block6personcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controlador2 {
    @Autowired
    private Servicio2 servicio2;

    @Autowired
    private Persona persona;

    @GetMapping("/controlador2/getPersona")
    Persona getPersona() {
        return servicio2.doblaEdad(persona);
    }
}

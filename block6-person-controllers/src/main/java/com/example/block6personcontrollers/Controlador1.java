package com.example.block6personcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controlador1 {
    @Autowired
    private Servicio1 servicio1;

    @Autowired
    private Persona persona;

    @GetMapping("/controlador1/addPersona")
    Persona addPersona(@RequestHeader("nombre") String nombre,
                       @RequestHeader("poblacion") String poblacion,
                       @RequestHeader("edad") String edad) {
        servicio1.rellenaPersona(persona, nombre, poblacion, edad);
        return persona;
    }
}

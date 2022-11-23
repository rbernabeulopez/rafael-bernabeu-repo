package com.example.block6personcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("controlador2")
public class Controlador2 {
    @Autowired
    private Servicio2 servicio2;

    @Autowired
    private Persona persona;

    @Autowired
    private List<Ciudad> ciudades;


    @GetMapping("getPersona")
    Persona getPersona() {
        return servicio2.doblaEdad(persona);
    }

    @GetMapping("getCiudad")
    List<Ciudad> getCiudades() {
        return ciudades;
    }
}

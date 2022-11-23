package com.example.block6personcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("controlador1")
public class Controlador1 {
    @Autowired
    private Servicio1 servicio1;

    @Autowired
    private Persona persona;

    @Autowired
    private List<Ciudad> ciudades;

    @GetMapping("addPersona")
    Persona addPersona(@RequestHeader("nombre") String nombre,
                       @RequestHeader("poblacion") String poblacion,
                       @RequestHeader("edad") String edad) {
        servicio1.rellenaPersona(persona, nombre, poblacion, edad);
        return persona;
    }

    @PostMapping("addCiudad")
    void addCiudad(@RequestBody Ciudad ciudad) {
        ciudades.add(ciudad);
    }
}

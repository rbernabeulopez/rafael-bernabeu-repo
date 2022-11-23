package com.example.block6personcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("controlador1")
public class Controlador1 {
    @Autowired
    private Servicio servicio;

    @GetMapping("addPersona")
    Persona addPersona(@RequestHeader("nombre") String nombre,
                       @RequestHeader("poblacion") String poblacion,
                       @RequestHeader("edad") String edad) {
        return servicio.creaPersona(nombre, poblacion, edad);
    }

    @PostMapping("addCiudad")
    void addCiudad(@RequestBody Ciudad ciudad) {
        servicio.addCiudad(ciudad);
    }
}

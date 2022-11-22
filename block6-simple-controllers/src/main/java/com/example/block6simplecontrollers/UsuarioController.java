package com.example.block6simplecontrollers;

import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {
    @GetMapping("/user/{nombre}")
    String saludar(@PathVariable String nombre) {
        return "Hola " + nombre;
    }

    @PostMapping("/useradd")
    Persona addEdad(@RequestBody Persona persona) {
        int nuevaEdad = Integer.parseInt(persona.getEdad()) + 1;
        persona.setEdad(Integer.toString(nuevaEdad));
        return persona;
    }
}

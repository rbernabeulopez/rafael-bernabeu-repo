package com.example.block6personcontrollers;

import org.springframework.stereotype.Service;

@Service
public class Servicio1 {

    public void rellenaPersona(Persona persona, String nombre, String poblacion, String edad) {
        persona.setNombre(nombre);
        persona.setCiudad(poblacion);
        persona.setEdad(edad);
    }
}

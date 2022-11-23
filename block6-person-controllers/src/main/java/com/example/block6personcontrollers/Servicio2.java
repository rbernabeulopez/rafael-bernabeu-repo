package com.example.block6personcontrollers;

import org.springframework.stereotype.Service;

@Service
public class Servicio2 {

    public Persona doblaEdad(Persona persona) {
        try {
            int edadDoblada = Integer.parseInt(persona.getEdad()) * 2;
            persona.setEdad(Integer.toString(edadDoblada));
            return persona;
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
            return persona;
        }
    }
}

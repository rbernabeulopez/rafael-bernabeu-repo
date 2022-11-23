package com.example.block6personcontrollers;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioImpl implements Servicio {

    private final Persona persona = new Persona();
    private List<Ciudad> ciudades;

    @Override
    public Persona creaPersona(String nombre, String poblacion, String edad) {
        persona.setNombre(nombre);
        persona.setCiudad(poblacion);
        persona.setEdad(edad);
        return persona;
    }

    @Override
    public Persona getPersonaConEdadDoble() {
        try {
            int edadDoblada = Integer.parseInt(persona.getEdad()) * 2;
            persona.setEdad(Integer.toString(edadDoblada));
            return persona;
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
            return persona;
        }
    }

    @Override
    public void addCiudad(Ciudad ciudad) {
        ciudades.add(ciudad);
    }

    @Override
    public List<Ciudad> getCiudades() {
        return ciudades;
    }
}

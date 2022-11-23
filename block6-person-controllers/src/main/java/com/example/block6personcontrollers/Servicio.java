package com.example.block6personcontrollers;

import java.util.List;

public interface Servicio {
    Persona creaPersona(String nombre, String poblacion, String edad);

    Persona getPersonaConEdadDoble();

    void addCiudad(Ciudad ciudad);

    List<Ciudad> getCiudades();
}

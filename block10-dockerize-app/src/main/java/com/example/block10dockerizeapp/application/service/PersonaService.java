package com.example.block10dockerizeapp.application.service;


import com.example.block10dockerizeapp.domain.Persona;

import java.util.List;

public interface PersonaService {

    void addPersona(Persona persona);
    void modifyById(long id, Persona persona);
    void deleteById(long id);
    Persona findById(long id);
    List<Persona> findByName(String name);
    List<Persona> findAll();
}

package com.example.block10dockerizeapp.application.repository;


import com.example.block10dockerizeapp.domain.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository {
    Optional<Persona> findById(long id);
    List<Persona> findByNombre(String nombre);
    List<Persona> findAll();
    void deleteById(long id);
    void save(Persona persona);
}

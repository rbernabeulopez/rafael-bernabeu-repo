package com.example.block7crud.application.repository;

import com.example.block7crud.domain.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository {
    Optional<Persona> findById(long id);
    List<Persona> findByNombre(String nombre);
    List<Persona> findAll();
    void deleteById(long id);

    void save(Persona persona);
}

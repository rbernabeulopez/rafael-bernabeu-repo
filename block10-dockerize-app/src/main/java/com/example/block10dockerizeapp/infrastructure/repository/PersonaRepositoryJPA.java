package com.example.block10dockerizeapp.infrastructure.repository;

import com.example.block10dockerizeapp.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaRepositoryJPA extends JpaRepository<Persona, Long> {
    List<Persona> findByNombre(String nombre);
}

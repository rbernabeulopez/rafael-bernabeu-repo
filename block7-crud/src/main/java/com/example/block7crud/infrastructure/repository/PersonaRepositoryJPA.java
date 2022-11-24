package com.example.block7crud.infrastructure.repository;

import com.example.block7crud.domain.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaRepositoryJPA extends JpaRepository<Persona, Long> {
    List<Persona> findByNombre(String nombre);
}

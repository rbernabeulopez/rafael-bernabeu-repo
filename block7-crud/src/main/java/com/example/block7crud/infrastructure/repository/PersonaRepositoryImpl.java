package com.example.block7crud.infrastructure.repository;

import com.example.block7crud.domain.Persona;
import com.example.block7crud.application.repository.PersonaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PersonaRepositoryImpl implements PersonaRepository {

    PersonaRepositoryJPA personaRepositoryJPA;

    @Override
    public Optional<Persona> findById(long id) {
        return personaRepositoryJPA.findById(id);
    }

    @Override
    public List<Persona> findByNombre(String nombre) {
        return personaRepositoryJPA.findByNombre(nombre);
    }

    @Override
    public List<Persona> findAll() {
        return personaRepositoryJPA.findAll();
    }

    @Override
    public void deleteById(long id) {
        personaRepositoryJPA.deleteById(id);
    }

    @Override
    public void save(Persona persona) {
        personaRepositoryJPA.save(persona);
    }
}

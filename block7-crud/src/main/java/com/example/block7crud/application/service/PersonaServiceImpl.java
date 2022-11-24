package com.example.block7crud.application.service;

import com.example.block7crud.application.exception.PersonaNotFoundException;
import com.example.block7crud.application.repository.PersonaRepository;
import com.example.block7crud.domain.Persona;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PersonaServiceImpl implements PersonaService {
    private PersonaRepository personaRepository;

    private Persona retrieveById(long id) {
        return personaRepository.findById(id).orElseThrow(() -> new PersonaNotFoundException("Persona no encontrada"));
    }

    @Override
    public void addPersona(Persona persona) {
        log.info("Saving person data {}", persona);
        personaRepository.save(persona);
    }

    @Override
    public void modifyById(long id, Persona persona) {
        log.info("Modifying person with id {} to data {}", id, persona);
        retrieveById(id);

        persona.setId(id);

        personaRepository.save(persona);
    }

    @Override
    public void deleteById(long id) {
        log.info("Deleting person with id {}", id);
        retrieveById(id);

        personaRepository.deleteById(id);
    }

    @Override
    public Persona findById(long id) {
        log.info("Finding person with id {}", id);
        return retrieveById(id);
    }


    @Override
    public List<Persona> findByName(String name) {
        log.info("Finding person with name {}", name);
        return personaRepository.findByNombre(name);
    }


    @Override
    public List<Persona> findAll() {
        log.info("Finding all person");
        return personaRepository.findAll();
    }
}

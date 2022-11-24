package com.example.block7crud.infrastructure.controller;

import com.example.block7crud.application.service.PersonaService;
import com.example.block7crud.domain.Persona;
import com.example.block7crud.infrastructure.controller.dto.PersonaOutputDto;
import com.example.block7crud.infrastructure.mapper.PersonaMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("persona")
public class RetrievePersonaController {
    private PersonaService personaService;

    @GetMapping("{id}")
    public PersonaOutputDto findById(@PathVariable long id) {
        Persona persona = personaService.findById(id);
        return PersonaMapper.INSTANCE.personaToPersonaOutputDto(persona);
    }

    @GetMapping("name/{name}")
    public List<PersonaOutputDto> findByName(@PathVariable String name) {
        List<Persona> personas = personaService.findByName(name);
        return personas.stream().map(PersonaMapper.INSTANCE::personaToPersonaOutputDto).toList();
    }

    @GetMapping
    public List<PersonaOutputDto> findByAll() {
        List<Persona> personas = personaService.findAll();
        return personas.stream().map(PersonaMapper.INSTANCE::personaToPersonaOutputDto).toList();
    }
}

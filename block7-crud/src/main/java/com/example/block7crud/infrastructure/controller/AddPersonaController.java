package com.example.block7crud.infrastructure.controller;

import com.example.block7crud.application.service.PersonaService;
import com.example.block7crud.domain.Persona;
import com.example.block7crud.infrastructure.controller.dto.PersonaInputDto;
import com.example.block7crud.infrastructure.mapper.PersonaMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("persona")
public class AddPersonaController {
    private PersonaService personaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody PersonaInputDto personaInputDto) {
        Persona persona = PersonaMapper.INSTANCE.personaInputDtoToPersona(personaInputDto);
        personaService.addPersona(persona);
    }
}

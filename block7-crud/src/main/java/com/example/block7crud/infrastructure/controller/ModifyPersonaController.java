package com.example.block7crud.infrastructure.controller;

import com.example.block7crud.application.service.PersonaService;
import com.example.block7crud.domain.Persona;
import com.example.block7crud.infrastructure.controller.dto.PersonaInputDto;
import com.example.block7crud.infrastructure.mapper.PersonaMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("persona")
public class ModifyPersonaController {
    private PersonaService personaService;


    @PutMapping("{id}")
    public void mofifyById(@PathVariable long id, @RequestBody PersonaInputDto personaInputDto) {
        Persona persona = PersonaMapper.INSTANCE.personaInputDtoToPersona(personaInputDto);
        personaService.modifyById(id, persona);
    }

}

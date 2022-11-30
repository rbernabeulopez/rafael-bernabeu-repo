package com.example.block10dockerizeapp.infrastructure.controller;

import com.example.block10dockerizeapp.application.service.PersonaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("persona")
public class DeletePersonaController {
    private PersonaService personaService;
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable long id) {
        personaService.deleteById(id);
    }
}

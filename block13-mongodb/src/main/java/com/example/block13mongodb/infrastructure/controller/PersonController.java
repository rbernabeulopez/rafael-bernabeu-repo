package com.example.block13mongodb.infrastructure.controller;

import com.example.block13mongodb.application.PersonService;
import com.example.block13mongodb.domain.entities.Person;
import com.example.block13mongodb.infrastructure.controller.dto.PersonInputDto;
import com.example.block13mongodb.infrastructure.controller.dto.PersonOutputDto;
import com.example.block13mongodb.infrastructure.mapper.PersonMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("person")
@AllArgsConstructor
public class PersonController {
    private PersonService personService;

    @GetMapping
    Page<PersonOutputDto> findAll(@RequestParam(name = "offset") int offset,
            @RequestParam(name = "pageSize") int pageSize) {
        return personService.findAll(offset, pageSize).map(PersonMapper.INSTANCE::personToPersonOutputDto);
    }

    @GetMapping("{id}")
    PersonOutputDto findById(@PathVariable String id) {
        Person person = personService.findById(id);
        return PersonMapper.INSTANCE.personToPersonOutputDto(person);
    }

    @PostMapping
    void save(@RequestBody PersonInputDto personInputDto) {
        Person person = PersonMapper.INSTANCE.personInputDtoToPerson(personInputDto);
        personService.create(person);
    }

    @DeleteMapping("{id}")
    void deleteById(@PathVariable String id) {
        personService.deleteById(id);
    }

    @PutMapping("{id}")
    void modifyById(@PathVariable String id, @RequestBody PersonInputDto personInputDto) {
        Person person = PersonMapper.INSTANCE.personInputDtoToPerson(personInputDto);
        personService.modifyById(id, person);
    }
}

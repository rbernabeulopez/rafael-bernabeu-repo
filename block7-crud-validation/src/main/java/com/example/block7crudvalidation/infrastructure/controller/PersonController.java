package com.example.block7crudvalidation.infrastructure.controller;

import com.example.block7crudvalidation.application.PersonService;
import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.infrastructure.controller.dto.PersonInputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.PersonOutputDto;
import com.example.block7crudvalidation.infrastructure.mapper.PersonMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("person")
@AllArgsConstructor
public class PersonController {
    private PersonService personService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void save(@RequestBody PersonInputDto personInputDto) {
        Person person = PersonMapper.INSTANCE.personInputDtoToPerson(personInputDto);
        personService.save(person);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable int id) {
        personService.deleteById(id);
    }

    @PutMapping("{id}")
    public void modifyById(@PathVariable int id, @RequestBody PersonInputDto personInputDto) {
        Person person = PersonMapper.INSTANCE.personInputDtoToPerson(personInputDto);
        personService.updateById(id, person);
    }

    @GetMapping("{id}")
    public PersonOutputDto searchById(@PathVariable int id) {
        Person person = personService.searchById(id);
        return PersonMapper.INSTANCE.personToPersonOutputDto(person);
    }

    @GetMapping("/name")
    public List<PersonOutputDto> searchByName(@RequestParam("name") String name) {
        List<Person> persons = personService.searchByName(name);
        return persons.stream().map(PersonMapper.INSTANCE::personToPersonOutputDto).toList();
    }

    @GetMapping
    public List<PersonOutputDto> searchAll() {
        List<Person> persons = personService.searchAll();
        return persons.stream().map(PersonMapper.INSTANCE::personToPersonOutputDto).toList();
    }
}

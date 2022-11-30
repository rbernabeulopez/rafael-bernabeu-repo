package com.example.block7crudvalidation.infrastructure.controller;

import com.example.block7crudvalidation.application.PersonService;
import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.infrastructure.controller.dto.input.PersonInputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.PersonOutputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.ProfessorOutputDto;
import com.example.block7crudvalidation.infrastructure.feign.ProfessorFeign;
import com.example.block7crudvalidation.infrastructure.mapper.PersonMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("person")
@AllArgsConstructor
public class PersonController {
    private PersonService personService;

    private ProfessorFeign professorFeign;

    private PersonOutputDto getPersonOutputDto(Person person, String ouputType) {
        return (Objects.equals(ouputType, "simple")) ? PersonMapper.INSTANCE.personToPersonOutputDto(person) :
                (Objects.equals(ouputType, "full")) ? PersonMapper.INSTANCE.personToPersonFullOutputDto(person) :
                null;
    }

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
    public PersonOutputDto searchById(@PathVariable int id,
          @RequestParam(value = "ouputType", defaultValue = "simple") String ouputType) {
        Person person = personService.searchById(id);
        return getPersonOutputDto(person, ouputType);
    }

    @GetMapping("/name")
    public List<PersonOutputDto> searchByName(@RequestParam("name") String name,
          @RequestParam(value = "ouputType", defaultValue = "simple") String ouputType) {
        List<Person> persons = personService.searchByName(name);
        return persons.stream().map(person -> getPersonOutputDto(person, ouputType)).toList();
    }

    @GetMapping
    public List<PersonOutputDto> searchAll(@RequestParam(value = "ouputType", defaultValue = "simple") String ouputType) {
        List<Person> persons = personService.searchAll();
        return persons.stream().map(person -> getPersonOutputDto(person, ouputType)).toList();
    }

    @GetMapping("/professor/{id}")
    ProfessorOutputDto getProfessor(@PathVariable String id) {
        return professorFeign.searchById(id, "simple");
    }
}

package com.example.block7crudvalidation.infrastructure.controller;

import com.example.block7crudvalidation.application.PersonService;
import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.infrastructure.controller.dto.input.PersonInputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.PersonOutputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.ProfessorOutputDto;
import com.example.block7crudvalidation.infrastructure.feign.ProfessorFeign;
import com.example.block7crudvalidation.infrastructure.mapper.PersonMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class PersonController {
    private PersonService personService;

    private ProfessorFeign professorFeign;

    private static final String SIMPLE_OUTPUT = "simple";
    private static final String FULL_OUTPUT = "full";

    private PersonOutputDto getPersonOutputDto(Person person, String ouputType) {
        if(Objects.equals(ouputType, SIMPLE_OUTPUT)) {
            return PersonMapper.INSTANCE.personToPersonOutputDto(person);
        }
        return (Objects.equals(ouputType, FULL_OUTPUT)) ? PersonMapper.INSTANCE.personToPersonFullOutputDto(person) :
                new PersonOutputDto();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("addperson")
    public void save(@RequestBody PersonInputDto personInputDto) {
        Person person = PersonMapper.INSTANCE.personInputDtoToPerson(personInputDto);
        personService.save(person);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("person/{id}")
    public void deleteById(@PathVariable int id) {
        personService.deleteById(id);
    }

    @PutMapping("person/{id}")
    public void modifyById(@PathVariable int id, @RequestBody PersonInputDto personInputDto) {
        Person person = PersonMapper.INSTANCE.personInputDtoToPerson(personInputDto);
        personService.updateById(id, person);
    }

    @GetMapping("person/{id}")
    public PersonOutputDto searchById(@PathVariable int id,
          @RequestParam(value = "ouputType", defaultValue = SIMPLE_OUTPUT) String ouputType) {
        Person person = personService.searchById(id);
        return getPersonOutputDto(person, ouputType);
    }

    @GetMapping("person/name")
    public List<PersonOutputDto> searchByName(@RequestParam("name") String name,
          @RequestParam(value = "ouputType", defaultValue = SIMPLE_OUTPUT) String ouputType) {
        List<Person> persons = personService.searchByName(name);
        return persons.stream().map(person -> getPersonOutputDto(person, ouputType)).toList();
    }

    @GetMapping("getall")
    public List<PersonOutputDto> searchAll(@RequestParam(value = "ouputType", defaultValue = SIMPLE_OUTPUT) String ouputType) {
        List<Person> persons = personService.searchAll();
        return persons.stream().map(person -> getPersonOutputDto(person, ouputType)).toList();
    }

    @GetMapping("person/professor/{id}")
    ProfessorOutputDto getProfessor(@PathVariable String id) {
        return professorFeign.searchById(id, SIMPLE_OUTPUT);
    }

    @GetMapping("person/fields")
    public List<PersonOutputDto> searchByFields(@RequestParam(name = "user", required = false) String user,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "surname", required = false) String surname,
            @RequestParam(name = "creationDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate creationDate,
            @RequestParam(name = "orderBy", defaultValue = "user", required = false) String orderBy) {
        List<Person> persons = personService.searchByFields(user, name, surname, creationDate, orderBy);
        return persons.stream().map(person -> getPersonOutputDto(person, SIMPLE_OUTPUT)).toList();
    }

    @GetMapping("person/paginated")
    public Page<PersonOutputDto> searchAllWithPagination(@RequestParam(value = "offset") int offset,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<Person> persons = personService.searchAllWithPagination(offset, pageSize);
        return persons.map(person -> getPersonOutputDto(person, SIMPLE_OUTPUT));
    }
}

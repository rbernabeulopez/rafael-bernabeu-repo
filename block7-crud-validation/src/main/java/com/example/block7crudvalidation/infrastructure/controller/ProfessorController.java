package com.example.block7crudvalidation.infrastructure.controller;

import com.example.block7crudvalidation.application.ProfessorService;
import com.example.block7crudvalidation.domain.entity.Professor;
import com.example.block7crudvalidation.infrastructure.controller.dto.input.ProfessorInputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.ProfessorOutputDto;
import com.example.block7crudvalidation.infrastructure.mapper.ProfessorMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("professor")
@AllArgsConstructor
public class ProfessorController {
    private ProfessorService professorService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void save(@RequestBody ProfessorInputDto professorInputDto) {
        Professor professor = ProfessorMapper.INSTANCE.professorInputDtoToProfessor(professorInputDto);
        professorService.save(professor, professorInputDto.getPersonId());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        professorService.deleteById(id);
    }

    @PutMapping("{id}")
    public void modifyById(@PathVariable String id, @RequestBody ProfessorInputDto professorInputDto) {
        Professor professor = ProfessorMapper.INSTANCE.professorInputDtoToProfessor(professorInputDto);
        professorService.updateById(id, professor);
    }

    @GetMapping("{id}")
    public ProfessorOutputDto searchById(@PathVariable String id,
                                                        @RequestParam(value = "ouputType", defaultValue = "simple") String ouputType) {
        Professor professor = professorService.searchById(id);
        if (Objects.equals(ouputType, "simple")) {
           return ProfessorMapper.INSTANCE.professorToProfessorOutputDto(professor);
        }
        return (Objects.equals(ouputType, "full")) ? ProfessorMapper.INSTANCE.professorToProfessorFullOutputDto(professor) :
                new ProfessorOutputDto();
    }

    @GetMapping
    public List<ProfessorOutputDto> searchAll() {
        List<Professor> professors = professorService.searchAll();
        return professors.stream().map(ProfessorMapper.INSTANCE::professorToProfessorOutputDto).toList();
    }
}

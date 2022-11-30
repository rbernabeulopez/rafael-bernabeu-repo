package com.example.block7crudvalidation.infrastructure.controller;

import com.example.block7crudvalidation.application.StudentsStudiesService;
import com.example.block7crudvalidation.domain.entity.StudentsStudies;
import com.example.block7crudvalidation.infrastructure.controller.dto.input.StudentsStudiesInputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.StudentsStudiesOutputDto;
import com.example.block7crudvalidation.infrastructure.mapper.StudentsStudiesMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("studentsStudies")
@AllArgsConstructor
public class StudentsStudiesController {
    private StudentsStudiesService studentsStudiesService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void save(@RequestBody StudentsStudiesInputDto studentsStudiesInputDto) {
        StudentsStudies studentsStudies =
                StudentsStudiesMapper.INSTANCE.studentStudiesInputDtoToStudentStudies(studentsStudiesInputDto);
        studentsStudiesService.save(studentsStudies, studentsStudiesInputDto.getStudentId());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        studentsStudiesService.deleteById(id);
    }

    @PutMapping("{id}")
    public void modifyById(@PathVariable String id, @RequestBody StudentsStudiesInputDto studentsStudiesInputDto) {
        StudentsStudies studentsStudies =
                StudentsStudiesMapper.INSTANCE.studentStudiesInputDtoToStudentStudies(studentsStudiesInputDto);
        studentsStudiesService.updateById(id, studentsStudies);
    }

    @GetMapping("{id}")
    public StudentsStudiesOutputDto searchById(@PathVariable String id) {
        StudentsStudies student = studentsStudiesService.searchById(id);
        return StudentsStudiesMapper.INSTANCE.studentStudiesToStudentStudiesOutputDto(student);
    }

    @GetMapping
    public List<StudentsStudiesOutputDto> searchAll() {
        List<StudentsStudies> studentsStudies = studentsStudiesService.searchAll();
        return studentsStudies.stream().map(StudentsStudiesMapper.INSTANCE::studentStudiesToStudentStudiesOutputDto).toList();
    }

    @GetMapping("student")
    public List<StudentsStudiesOutputDto> searchByStudentId(@RequestParam(value = "student_id") String studentId) {
        List<StudentsStudies> studentsStudies = studentsStudiesService.findByStudentId(studentId);
        return studentsStudies.stream().map(StudentsStudiesMapper.INSTANCE::studentStudiesToStudentStudiesOutputDto).toList();
    }
}

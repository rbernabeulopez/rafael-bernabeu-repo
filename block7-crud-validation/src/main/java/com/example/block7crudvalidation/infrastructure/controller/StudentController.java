package com.example.block7crudvalidation.infrastructure.controller;

import com.example.block7crudvalidation.application.StudentService;
import com.example.block7crudvalidation.domain.entity.Student;
import com.example.block7crudvalidation.infrastructure.controller.dto.input.StudentInputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.StudentOutputDto;
import com.example.block7crudvalidation.infrastructure.mapper.StudentMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("student")
@AllArgsConstructor
public class StudentController {
    private StudentService studentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void save(@RequestBody StudentInputDto studentInputDto) {
        Student student = StudentMapper.INSTANCE.studentInputDtoToStudent(studentInputDto);
        studentService.save(student, studentInputDto.getPersonId(), studentInputDto.getProfessorId());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        studentService.deleteById(id);
    }

    @PutMapping("{id}")
    public void modifyById(@PathVariable String id, @RequestBody StudentInputDto studentInputDto) {
        Student student = StudentMapper.INSTANCE.studentInputDtoToStudent(studentInputDto);
        studentService.updateById(id, student);
    }

    @GetMapping("{id}")
    public StudentOutputDto searchById(@PathVariable String id,
           @RequestParam(value = "ouputType", defaultValue = "simple") String ouputType) {
        Student student = studentService.searchById(id);
        return (Objects.equals(ouputType, "simple")) ? StudentMapper.INSTANCE.studentToStudentOutputDto(student) :
                (Objects.equals(ouputType, "full")) ? StudentMapper.INSTANCE.studentToStudentFullOutputDto(student) :
                null;
    }

    @GetMapping
    public List<StudentOutputDto> searchAll() {
        List<Student> students = studentService.searchAll();
        return students.stream().map(StudentMapper.INSTANCE::studentToStudentOutputDto).toList();
    }

    @PostMapping("unassign")
    public void unasignStudies(List<String> studiesIds) {
        studentService.unassignStudies(studiesIds);
    }
}

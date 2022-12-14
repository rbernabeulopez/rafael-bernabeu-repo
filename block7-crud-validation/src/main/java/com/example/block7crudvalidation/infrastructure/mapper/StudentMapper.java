package com.example.block7crudvalidation.infrastructure.mapper;

import com.example.block7crudvalidation.domain.entity.Student;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.StudentFullOutputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.input.StudentInputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.StudentOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    Student studentInputDtoToStudent(StudentInputDto studentInputDto);

    StudentOutputDto studentToStudentOutputDto(Student student);

    StudentFullOutputDto studentToStudentFullOutputDto(Student student);
}

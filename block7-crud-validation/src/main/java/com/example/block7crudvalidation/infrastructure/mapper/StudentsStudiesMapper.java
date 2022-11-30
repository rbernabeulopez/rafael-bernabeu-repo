package com.example.block7crudvalidation.infrastructure.mapper;

import com.example.block7crudvalidation.domain.entity.StudentsStudies;
import com.example.block7crudvalidation.infrastructure.controller.dto.input.StudentsStudiesInputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.StudentsStudiesOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentsStudiesMapper {
    StudentsStudiesMapper INSTANCE = Mappers.getMapper(StudentsStudiesMapper.class);

    StudentsStudies studentStudiesInputDtoToStudentStudies(StudentsStudiesInputDto studentInputDto);

    StudentsStudiesOutputDto studentStudiesToStudentStudiesOutputDto(StudentsStudies studentsStudies);

}

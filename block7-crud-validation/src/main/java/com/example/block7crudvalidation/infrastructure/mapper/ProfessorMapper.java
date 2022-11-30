package com.example.block7crudvalidation.infrastructure.mapper;

import com.example.block7crudvalidation.domain.entity.Professor;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.ProfessorFullOutputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.input.ProfessorInputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.ProfessorOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfessorMapper {
    ProfessorMapper INSTANCE = Mappers.getMapper(ProfessorMapper.class);

    Professor professorInputDtoToProfessor(ProfessorInputDto professorInputDto);

    ProfessorOutputDto professorToProfessorOutputDto(Professor professor);

    ProfessorFullOutputDto professorToProfessorFullOutputDto(Professor professor);
}

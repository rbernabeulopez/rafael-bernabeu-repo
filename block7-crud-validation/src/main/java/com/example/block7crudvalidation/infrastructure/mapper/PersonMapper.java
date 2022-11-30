package com.example.block7crudvalidation.infrastructure.mapper;

import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.infrastructure.controller.dto.input.PersonInputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.PersonFullOutputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.output.PersonOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
    PersonOutputDto personToPersonOutputDto(Person person);
    PersonFullOutputDto personToPersonFullOutputDto(Person person);
    Person personInputDtoToPerson(PersonInputDto personInputDto);
}

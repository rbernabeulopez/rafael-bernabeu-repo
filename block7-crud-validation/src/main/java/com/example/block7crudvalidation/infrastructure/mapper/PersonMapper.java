package com.example.block7crudvalidation.infrastructure.mapper;

import com.example.block7crudvalidation.domain.entity.Person;
import com.example.block7crudvalidation.infrastructure.controller.dto.PersonInputDto;
import com.example.block7crudvalidation.infrastructure.controller.dto.PersonOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);
    PersonOutputDto personToPersonOutputDto(Person person);
    Person personInputDtoToPerson(PersonInputDto personInputDto);
}

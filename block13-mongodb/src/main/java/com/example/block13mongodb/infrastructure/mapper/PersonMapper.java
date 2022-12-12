package com.example.block13mongodb.infrastructure.mapper;

import com.example.block13mongodb.domain.entities.Person;
import com.example.block13mongodb.infrastructure.controller.dto.PersonInputDto;
import com.example.block13mongodb.infrastructure.controller.dto.PersonOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonOutputDto personToPersonOutputDto(Person person);
    Person personInputDtoToPerson(PersonInputDto person);
}

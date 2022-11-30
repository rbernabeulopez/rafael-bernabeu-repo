package com.example.block10dockerizeapp.infrastructure.mapper;

import com.example.block10dockerizeapp.domain.Persona;
import com.example.block10dockerizeapp.infrastructure.controller.dto.PersonaInputDto;
import com.example.block10dockerizeapp.infrastructure.controller.dto.PersonaOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonaMapper {
    PersonaMapper INSTANCE = Mappers.getMapper(PersonaMapper.class);
    Persona personaInputDtoToPersona(PersonaInputDto persona);
    PersonaOutputDto personaToPersonaOutputDto(Persona persona);
}

package com.example.block7crud.infrastructure.mapper;

import com.example.block7crud.domain.Persona;
import com.example.block7crud.infrastructure.controller.dto.PersonaInputDto;
import com.example.block7crud.infrastructure.controller.dto.PersonaOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonaMapper {
    PersonaMapper INSTANCE = Mappers.getMapper(PersonaMapper.class);
    Persona personaInputDtoToPersona(PersonaInputDto persona);
    PersonaOutputDto personaToPersonaOutputDto(Persona persona);
}

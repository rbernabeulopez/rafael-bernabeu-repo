package org.example.backend.infrastructure.mapper;

import org.example.backend.domain.entity.Client;
import org.example.backend.infrastructure.controller.dto.ClientInputDto;
import org.example.openfeignclients.backend.dto.ClientOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client clientInputDtoToClient(ClientInputDto clientInputDto);

    ClientOutputDto clientToClientOutputDto(Client client);
}

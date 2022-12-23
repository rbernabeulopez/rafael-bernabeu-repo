package org.example.backend.infrastructure.mapper;

import org.example.backend.domain.entity.Trip;
import org.example.backend.infrastructure.controller.dto.TripInputDto;
import org.example.openfeignclients.backend.dto.TripOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TripMapper {
    TripMapper INSTANCE = Mappers.getMapper(TripMapper.class);

    Trip tripInputDtoToTrip(TripInputDto tripInputDto);

    TripOutputDto tripToTripOutputDto(Trip trip);
}

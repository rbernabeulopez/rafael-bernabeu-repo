package com.example.examen_jpa_cascada.infrastructure.mapper;

import com.example.examen_jpa_cascada.domain.entities.CabeceraFra;
import com.example.examen_jpa_cascada.domain.entities.LineaFra;
import com.example.examen_jpa_cascada.infrastructure.controller.dto.FacturaOutputDto;
import com.example.examen_jpa_cascada.infrastructure.controller.dto.LineaInputDto;
import com.example.examen_jpa_cascada.infrastructure.controller.dto.LineaOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FacturaMapper {
    FacturaMapper INSTANCE = Mappers.getMapper(FacturaMapper.class);

    @Mapping(source = "proNomb", target = "producto")
    @Mapping(source = "precio", target = "importe")
    LineaOutputDto lineasFraToLineaOutputDto(LineaFra lineaFra);

    FacturaOutputDto cabeceraFraToFacturaOutputDto(CabeceraFra cabeceraFra);

    @Mapping(source = "producto", target = "proNomb")
    @Mapping(source = "importe", target = "precio")
    LineaFra lineaInputDtoToLineasFra(LineaInputDto lineaInputDto);
}
